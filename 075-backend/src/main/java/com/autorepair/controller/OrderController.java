package com.autorepair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.autorepair.common.BusinessException;
import com.autorepair.common.Result;
import com.autorepair.entity.RepairAppointment;
import com.autorepair.service.RepairAppointmentService;
import com.autorepair.vo.RepairAppointmentVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private RepairAppointmentService orderService;

    @PostMapping
    public Result<?> create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (params.get("serviceId") == null || params.get("quantity") == null
                || params.get("appointmentDate") == null || params.get("appointmentTime") == null
                || params.get("plateNo") == null || params.get("vehicleModel") == null) {
            throw new BusinessException("缺少预约参数");
        }
        Long serviceId = ((Number) params.get("serviceId")).longValue();
        Integer quantity = ((Number) params.get("quantity")).intValue();
        String remark = params.get("remark") == null ? null : params.get("remark").toString();
        LocalDate appointmentDate = LocalDate.parse(params.get("appointmentDate").toString());
        LocalTime appointmentTime = LocalTime.parse(params.get("appointmentTime").toString());
        String plateNo = params.get("plateNo").toString();
        String vehicleModel = params.get("vehicleModel").toString();
        String faultDesc = params.get("faultDesc") == null ? null : params.get("faultDesc").toString();
        RepairAppointment order = orderService.create(userId, serviceId, quantity, remark, appointmentDate, appointmentTime, plateNo, vehicleModel, faultDesc);
        return Result.success(order);
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String orderNo,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) Long userId,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        Page<RepairAppointmentVO> page = orderService.page(pageNum, pageSize, orderNo, status, userId);
        return Result.success(page);
    }

    @GetMapping("/my")
    public Result<?> my(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) Integer status,
                        @RequestParam(required = false) String orderNo,
                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Page<RepairAppointmentVO> page = orderService.myOrders(userId, pageNum, pageSize, status, orderNo);
        return Result.success(page);
    }

    @GetMapping("/sale")
    public Result<?> sale(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) String orderNo,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Page<RepairAppointmentVO> page = orderService.saleOrders(userId, pageNum, pageSize, status, orderNo);
        return Result.success(page);
    }

    @PutMapping("/pay/{id}")
    public Result<?> pay(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        orderService.pay(id, userId);
        return Result.success();
    }

    @PutMapping("/deliver/{id}")
    public Result<?> deliver(@PathVariable Long id, HttpServletRequest request) {
        Long operatorId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        orderService.deliver(id, operatorId, role);
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    public Result<?> complete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        orderService.complete(id, userId);
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    public Result<?> cancel(@PathVariable Long id, HttpServletRequest request) {
        Long operatorId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        orderService.cancel(id, operatorId, role);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}



