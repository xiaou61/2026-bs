package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.*;
import com.xiaou.service.*;
import com.xiaou.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/api/express")
@CrossOrigin
public class ExpressController {

    @Autowired
    private ExpressService expressService;

    @Autowired
    private UserService userService;

    @Autowired
    private StationService stationService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private OperationLogService operationLogService;

    @PostMapping("/in")
    public Result<?> expressIn(@RequestBody Express express, @RequestHeader("Authorization") String token) {
        try {
            Long userId = JwtUtil.getUserId(token);
            String username = JwtUtil.getUsername(token);

            QueryWrapper<Express> wrapper = new QueryWrapper<>();
            wrapper.eq("tracking_number", express.getTrackingNumber());
            if (expressService.count(wrapper) > 0) {
                return Result.error("快递单号已存在");
            }

            String pickupCode = generatePickupCode();
            express.setPickupCode(pickupCode);
            express.setStatus(0);
            express.setInTime(LocalDateTime.now());
            express.setInOperatorId(userId);
            express.setInOperatorName(username);
            express.setOverdueDays(0);
            express.setOverdueFee(BigDecimal.ZERO);
            express.setIsNotified(0);

            QueryWrapper<User> userWrapper = new QueryWrapper<>();
            userWrapper.eq("phone", express.getRecipientPhone());
            User recipient = userService.getOne(userWrapper);
            if (recipient != null) {
                express.setRecipientId(recipient.getId());
                express.setRecipientName(recipient.getRealName());
            }

            expressService.save(express);

            Station station = stationService.getById(express.getStationId());
            if (station != null) {
                station.setCurrentStock(station.getCurrentStock() + 1);
                stationService.updateById(station);
            }

            if (recipient != null) {
                Notification notification = new Notification();
                notification.setUserId(recipient.getId());
                notification.setType("ARRIVAL");
                notification.setTitle("快递到达通知");
                notification.setContent(String.format("您的%s快递(%s)已到达%s，取件码：%s，请及时取件。",
                        express.getExpressCompany(), express.getTrackingNumber(),
                        station != null ? station.getName() : "", pickupCode));
                notification.setExpressId(express.getId());
                notification.setIsRead(0);
                notification.setSendMethod("SYSTEM");
                notification.setSendStatus(1);
                notificationService.save(notification);
            }

            OperationLog log = new OperationLog();
            log.setOperatorId(userId);
            log.setOperatorName(username);
            log.setOperationType("IN");
            log.setOperationDesc("快递入库: " + express.getTrackingNumber());
            log.setExpressId(express.getId());
            operationLogService.save(log);

            Map<String, Object> data = new HashMap<>();
            data.put("pickupCode", pickupCode);
            data.put("expressId", express.getId());

            return Result.success("入库成功", data);
        } catch (Exception e) {
            return Result.error("入库失败: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<?> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String expressCompany,
            @RequestParam(required = false) Long stationId,
            @RequestParam(required = false) String keyword) {
        
        Page<Express> pageObj = new Page<>(page, size);
        QueryWrapper<Express> wrapper = new QueryWrapper<>();
        
        if (status != null) {
            wrapper.eq("status", status);
        }
        
        if (expressCompany != null && !expressCompany.isEmpty()) {
            wrapper.eq("express_company", expressCompany);
        }
        
        if (stationId != null) {
            wrapper.eq("station_id", stationId);
        }
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like("tracking_number", keyword)
                    .or().like("recipient_name", keyword)
                    .or().like("recipient_phone", keyword)
                    .or().like("pickup_code", keyword));
        }
        
        wrapper.orderByDesc("in_time");
        Page<Express> result = expressService.page(pageObj, wrapper);
        
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        Express express = expressService.getById(id);
        if (express == null) {
            return Result.error("快递不存在");
        }
        return Result.success(express);
    }

    @PostMapping("/verify-pickup")
    public Result<?> verifyPickup(@RequestBody Map<String, String> params) {
        String pickupCode = params.get("pickupCode");
        
        QueryWrapper<Express> wrapper = new QueryWrapper<>();
        wrapper.eq("pickup_code", pickupCode);
        Express express = expressService.getOne(wrapper);
        
        if (express == null) {
            return Result.error("取件码不存在");
        }
        
        if (express.getStatus() != 0) {
            return Result.error("快递已取件");
        }

        long days = ChronoUnit.DAYS.between(express.getInTime().toLocalDate(), LocalDateTime.now().toLocalDate());
        int overdueDays = (int) Math.max(0, days - 3);
        BigDecimal overdueFee = calculateOverdueFee(overdueDays);
        
        express.setOverdueDays(overdueDays);
        express.setOverdueFee(overdueFee);
        
        return Result.success(express);
    }

    @PostMapping("/pickup")
    public Result<?> pickup(@RequestBody Map<String, Object> params, @RequestHeader("Authorization") String token) {
        try {
            Long userId = JwtUtil.getUserId(token);
            String username = JwtUtil.getUsername(token);
            String pickupCode = (String) params.get("pickupCode");
            
            QueryWrapper<Express> wrapper = new QueryWrapper<>();
            wrapper.eq("pickup_code", pickupCode);
            Express express = expressService.getOne(wrapper);
            
            if (express == null) {
                return Result.error("取件码不存在");
            }
            
            if (express.getStatus() != 0) {
                return Result.error("快递已取件");
            }

            long days = ChronoUnit.DAYS.between(express.getInTime().toLocalDate(), LocalDateTime.now().toLocalDate());
            int overdueDays = (int) Math.max(0, days - 3);
            BigDecimal overdueFee = calculateOverdueFee(overdueDays);

            express.setStatus(1);
            express.setOutTime(LocalDateTime.now());
            express.setOutOperatorId(userId);
            express.setOutOperatorName(username);
            express.setOverdueDays(overdueDays);
            express.setOverdueFee(overdueFee);
            expressService.updateById(express);

            Station station = stationService.getById(express.getStationId());
            if (station != null) {
                station.setCurrentStock(Math.max(0, station.getCurrentStock() - 1));
                stationService.updateById(station);
            }

            OperationLog log = new OperationLog();
            log.setOperatorId(userId);
            log.setOperatorName(username);
            log.setOperationType("OUT");
            log.setOperationDesc("快递取件: " + express.getTrackingNumber());
            log.setExpressId(express.getId());
            operationLogService.save(log);

            Map<String, Object> data = new HashMap<>();
            data.put("trackingNumber", express.getTrackingNumber());
            data.put("overdueFee", overdueFee);
            
            return Result.success("取件成功", data);
        } catch (Exception e) {
            return Result.error("取件失败: " + e.getMessage());
        }
    }

    @GetMapping("/my-packages")
    public Result<?> myPackages(@RequestHeader("Authorization") String token) {
        try {
            Long userId = JwtUtil.getUserId(token);
            
            QueryWrapper<Express> wrapper = new QueryWrapper<>();
            wrapper.eq("recipient_id", userId);
            wrapper.eq("status", 0);
            wrapper.orderByDesc("in_time");
            
            return Result.success(expressService.list(wrapper));
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    @GetMapping("/my-history")
    public Result<?> myHistory(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestHeader("Authorization") String token) {
        try {
            Long userId = JwtUtil.getUserId(token);
            
            Page<Express> pageObj = new Page<>(page, size);
            QueryWrapper<Express> wrapper = new QueryWrapper<>();
            wrapper.eq("recipient_id", userId);
            wrapper.eq("status", 1);
            wrapper.orderByDesc("out_time");
            
            return Result.success(expressService.page(pageObj, wrapper));
        } catch (Exception e) {
            return Result.error("查询失败");
        }
    }

    private String generatePickupCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    private BigDecimal calculateOverdueFee(int overdueDays) {
        if (overdueDays <= 0) {
            return BigDecimal.ZERO;
        }
        
        if (overdueDays <= 4) {
            return BigDecimal.valueOf(overdueDays * 1.0);
        } else {
            return BigDecimal.valueOf(4 * 1.0 + (overdueDays - 4) * 2.0);
        }
    }

    @PostMapping("/batch-import")
    public Result<?> batchImport(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token) {
        try {
            Long userId = JwtUtil.getUserId(token);
            String username = JwtUtil.getUsername(token);

            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            List<Map<String, Object>> successList = new ArrayList<>();
            List<Map<String, Object>> failList = new ArrayList<>();
            int successCount = 0;
            int failCount = 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    String trackingNumber = getCellValue(row.getCell(0));
                    String expressCompany = getCellValue(row.getCell(1));
                    String recipientPhone = getCellValue(row.getCell(2));
                    String stationIdStr = getCellValue(row.getCell(3));
                    String shelfLocation = getCellValue(row.getCell(4));

                    if (trackingNumber == null || trackingNumber.isEmpty()) {
                        Map<String, Object> failItem = new HashMap<>();
                        failItem.put("row", i + 1);
                        failItem.put("reason", "快递单号不能为空");
                        failList.add(failItem);
                        failCount++;
                        continue;
                    }

                    QueryWrapper<Express> wrapper = new QueryWrapper<>();
                    wrapper.eq("tracking_number", trackingNumber);
                    if (expressService.count(wrapper) > 0) {
                        Map<String, Object> failItem = new HashMap<>();
                        failItem.put("row", i + 1);
                        failItem.put("trackingNumber", trackingNumber);
                        failItem.put("reason", "快递单号已存在");
                        failList.add(failItem);
                        failCount++;
                        continue;
                    }

                    Express express = new Express();
                    express.setTrackingNumber(trackingNumber);
                    express.setExpressCompany(expressCompany);
                    express.setRecipientPhone(recipientPhone);
                    express.setStationId(Long.parseLong(stationIdStr));
                    express.setShelfLocation(shelfLocation);
                    express.setPickupCode(generatePickupCode());
                    express.setStatus(0);
                    express.setInTime(LocalDateTime.now());
                    express.setInOperatorId(userId);
                    express.setInOperatorName(username);
                    express.setOverdueDays(0);
                    express.setOverdueFee(BigDecimal.ZERO);
                    express.setIsNotified(0);

                    QueryWrapper<User> userWrapper = new QueryWrapper<>();
                    userWrapper.eq("phone", recipientPhone);
                    User recipient = userService.getOne(userWrapper);
                    if (recipient != null) {
                        express.setRecipientId(recipient.getId());
                        express.setRecipientName(recipient.getRealName());

                        Notification notification = new Notification();
                        notification.setUserId(recipient.getId());
                        notification.setType("ARRIVAL");
                        notification.setTitle("快递到达通知");
                        notification.setContent(String.format("您的%s快递(%s)已到达，取件码：%s，请及时取件。",
                                expressCompany, trackingNumber, express.getPickupCode()));
                        notification.setExpressId(express.getId());
                        notification.setIsRead(0);
                        notification.setSendMethod("SYSTEM");
                        notification.setSendStatus(1);
                        notificationService.save(notification);
                    }

                    expressService.save(express);

                    Station station = stationService.getById(express.getStationId());
                    if (station != null) {
                        station.setCurrentStock(station.getCurrentStock() + 1);
                        stationService.updateById(station);
                    }

                    Map<String, Object> successItem = new HashMap<>();
                    successItem.put("trackingNumber", trackingNumber);
                    successItem.put("pickupCode", express.getPickupCode());
                    successList.add(successItem);
                    successCount++;

                } catch (Exception e) {
                    Map<String, Object> failItem = new HashMap<>();
                    failItem.put("row", i + 1);
                    failItem.put("reason", "数据格式错误: " + e.getMessage());
                    failList.add(failItem);
                    failCount++;
                }
            }

            workbook.close();
            inputStream.close();

            Map<String, Object> result = new HashMap<>();
            result.put("successCount", successCount);
            result.put("failCount", failCount);
            result.put("successList", successList);
            result.put("failList", failList);

            return Result.success("导入完成", result);
        } catch (Exception e) {
            return Result.error("导入失败: " + e.getMessage());
        }
    }

    @GetMapping("/export")
    public void export(
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String expressCompany,
            @RequestParam(required = false) Long stationId,
            @RequestParam(required = false) String keyword,
            HttpServletResponse response) {
        try {
            QueryWrapper<Express> wrapper = new QueryWrapper<>();
            
            if (status != null) {
                wrapper.eq("status", status);
            }
            
            if (expressCompany != null && !expressCompany.isEmpty()) {
                wrapper.eq("express_company", expressCompany);
            }
            
            if (stationId != null) {
                wrapper.eq("station_id", stationId);
            }
            
            if (keyword != null && !keyword.isEmpty()) {
                wrapper.and(w -> w.like("tracking_number", keyword)
                        .or().like("recipient_name", keyword)
                        .or().like("recipient_phone", keyword)
                        .or().like("pickup_code", keyword));
            }
            
            wrapper.orderByDesc("in_time");
            List<Express> list = expressService.list(wrapper);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("快递列表");

            String[] headers = {"快递单号", "快递公司", "取件码", "收件人", "手机号", "代收点ID", 
                               "货架位置", "状态", "入库时间", "取件时间", "超期天数", "超期费用"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < list.size(); i++) {
                Express express = list.get(i);
                Row row = sheet.createRow(i + 1);
                
                row.createCell(0).setCellValue(express.getTrackingNumber());
                row.createCell(1).setCellValue(express.getExpressCompany());
                row.createCell(2).setCellValue(express.getPickupCode());
                row.createCell(3).setCellValue(express.getRecipientName());
                row.createCell(4).setCellValue(express.getRecipientPhone());
                row.createCell(5).setCellValue(express.getStationId());
                row.createCell(6).setCellValue(express.getShelfLocation());
                row.createCell(7).setCellValue(getStatusText(express.getStatus()));
                row.createCell(8).setCellValue(express.getInTime() != null ? express.getInTime().format(formatter) : "");
                row.createCell(9).setCellValue(express.getOutTime() != null ? express.getOutTime().format(formatter) : "");
                row.createCell(10).setCellValue(express.getOverdueDays());
                row.createCell(11).setCellValue(express.getOverdueFee() != null ? express.getOverdueFee().doubleValue() : 0);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            String fileName = "快递列表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/download-template")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("快递导入模板");

            String[] headers = {"快递单号*", "快递公司*", "收件人手机号*", "代收点ID*", "货架位置*"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            Row exampleRow = sheet.createRow(1);
            exampleRow.createCell(0).setCellValue("SF1234567890");
            exampleRow.createCell(1).setCellValue("顺丰速运");
            exampleRow.createCell(2).setCellValue("13800138000");
            exampleRow.createCell(3).setCellValue("1");
            exampleRow.createCell(4).setCellValue("A-01-01");

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("快递导入模板.xlsx", StandardCharsets.UTF_8));

            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            default:
                return "";
        }
    }

    private String getStatusText(Integer status) {
        switch (status) {
            case 0: return "待取件";
            case 1: return "已取件";
            case 2: return "超期";
            case 3: return "退件";
            case 4: return "取消";
            default: return "未知";
        }
    }
}

