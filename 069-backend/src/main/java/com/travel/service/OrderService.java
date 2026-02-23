package com.teacher.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.common.BusinessException;
import com.teacher.common.PageResult;
import com.teacher.entity.ScenicSpot;
import com.teacher.entity.TravelOrder;
import com.teacher.entity.Traveler;
import com.teacher.entity.User;
import com.teacher.mapper.TravelOrderMapper;
import com.teacher.mapper.TravelerMapper;
import com.teacher.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    public static final String STATUS_WAIT_PAY = "WAIT_PAY";
    public static final String STATUS_PAID = "PAID";
    public static final String STATUS_CANCELED = "CANCELED";
    public static final String STATUS_FINISHED = "FINISHED";

    @Resource
    private TravelOrderMapper travelOrderMapper;

    @Resource
    private TravelerMapper travelerMapper;

    @Resource
    private SpotService spotService;

    @Resource
    private UserMapper userMapper;

    public TravelOrder create(Long userId, TravelOrder order) {
        if (order == null || order.getTravelerId() == null || order.getSpotId() == null || order.getTravelDate() == null) {
            throw new BusinessException("订单参数不完整");
        }
        Traveler traveler = travelerMapper.selectById(order.getTravelerId());
        if (traveler == null || !traveler.getUserId().equals(userId)) {
            throw new BusinessException("出行人不存在");
        }
        ScenicSpot spot = spotService.getEnabledById(order.getSpotId());
        if (spot == null) {
            throw new BusinessException("景点不存在或已下架");
        }
        int quantity = order.getQuantity() == null ? 1 : order.getQuantity();
        if (quantity <= 0) {
            throw new BusinessException("出行人数必须大于0");
        }
        order.setOrderNo("TR" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(4));
        order.setUserId(userId);
        order.setQuantity(quantity);
        order.setStatus(STATUS_WAIT_PAY);
        order.setContactName(StringUtils.hasText(order.getContactName()) ? order.getContactName().trim() : traveler.getName());
        order.setContactPhone(StringUtils.hasText(order.getContactPhone()) ? order.getContactPhone().trim() : traveler.getPhone());
        order.setRemark(StringUtils.hasText(order.getRemark()) ? order.getRemark().trim() : "");
        BigDecimal price = spot.getPrice() == null ? BigDecimal.ZERO : spot.getPrice();
        order.setTotalAmount(price.multiply(BigDecimal.valueOf(quantity)));
        travelOrderMapper.insert(order);
        return order;
    }

    public PageResult<Map<String, Object>> page(Integer pageNum, Integer pageSize, String orderNo, String status, Long userId) {
        Page<TravelOrder> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TravelOrder> wrapper = buildQueryWrapper(orderNo, status, userId);
        Page<TravelOrder> resultPage = travelOrderMapper.selectPage(page, wrapper);
        return buildPageResult(resultPage);
    }

    public PageResult<Map<String, Object>> myPage(Long userId, Integer pageNum, Integer pageSize, String orderNo, String status) {
        return page(pageNum, pageSize, orderNo, status, userId);
    }

    public void cancel(Long userId, String role, Long id) {
        TravelOrder db = travelOrderMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("订单不存在");
        }
        if ("USER".equals(role) && !db.getUserId().equals(userId)) {
            throw new BusinessException("无权限操作");
        }
        if (!STATUS_WAIT_PAY.equals(db.getStatus())) {
            throw new BusinessException("当前订单状态不可取消");
        }
        db.setStatus(STATUS_CANCELED);
        travelOrderMapper.updateById(db);
    }

    public void pay(Long userId, Long id) {
        TravelOrder db = travelOrderMapper.selectById(id);
        if (db == null || !db.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (!STATUS_WAIT_PAY.equals(db.getStatus())) {
            throw new BusinessException("当前订单不可支付");
        }
        db.setStatus(STATUS_PAID);
        db.setPayTime(LocalDateTime.now());
        travelOrderMapper.updateById(db);
    }

    public void finish(Long userId, Long id) {
        TravelOrder db = travelOrderMapper.selectById(id);
        if (db == null || !db.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (!STATUS_PAID.equals(db.getStatus())) {
            throw new BusinessException("当前订单不可完成");
        }
        db.setStatus(STATUS_FINISHED);
        db.setFinishTime(LocalDateTime.now());
        travelOrderMapper.updateById(db);
    }

    public Long countAll() {
        return travelOrderMapper.selectCount(null);
    }

    public Long countByUser(Long userId) {
        return travelOrderMapper.selectCount(new LambdaQueryWrapper<TravelOrder>().eq(TravelOrder::getUserId, userId));
    }

    public Long countByUserAndStatus(Long userId, String status) {
        return travelOrderMapper.selectCount(new LambdaQueryWrapper<TravelOrder>()
                .eq(TravelOrder::getUserId, userId)
                .eq(TravelOrder::getStatus, status));
    }

    public String exportCsv(String orderNo, String status, Long userId) {
        List<Map<String, Object>> records = buildRecords(travelOrderMapper.selectList(buildQueryWrapper(orderNo, status, userId)));
        StringBuilder builder = new StringBuilder();
        builder.append("订单号,用户,景点,出行人,出行日期,人数,金额,状态,联系人,联系电话,创建时间\n");
        for (Map<String, Object> row : records) {
            builder.append(csvValue(row.get("orderNo"))).append(",");
            builder.append(csvValue(row.get("userName"))).append(",");
            builder.append(csvValue(row.get("spotName"))).append(",");
            builder.append(csvValue(row.get("travelerName"))).append(",");
            builder.append(csvValue(row.get("travelDate"))).append(",");
            builder.append(csvValue(row.get("quantity"))).append(",");
            builder.append(csvValue(row.get("totalAmount"))).append(",");
            builder.append(csvValue(statusText(String.valueOf(row.get("status"))))).append(",");
            builder.append(csvValue(row.get("contactName"))).append(",");
            builder.append(csvValue(row.get("contactPhone"))).append(",");
            builder.append(csvValue(row.get("createTime"))).append("\n");
        }
        return builder.toString();
    }

    private PageResult<Map<String, Object>> buildPageResult(Page<TravelOrder> resultPage) {
        List<Map<String, Object>> records = buildRecords(resultPage.getRecords());
        PageResult<Map<String, Object>> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(records);
        return result;
    }

    private List<Map<String, Object>> buildRecords(List<TravelOrder> orders) {
        List<Map<String, Object>> records = new ArrayList<>();
        if (orders.isEmpty()) {
            return records;
        }
        Set<Long> spotIds = orders.stream().map(TravelOrder::getSpotId).collect(Collectors.toSet());
        Set<Long> travelerIds = orders.stream().map(TravelOrder::getTravelerId).collect(Collectors.toSet());
        Set<Long> userIds = orders.stream().map(TravelOrder::getUserId).collect(Collectors.toSet());
        Map<Long, ScenicSpot> spotMap = spotService.mapByIds(spotIds);
        Map<Long, Traveler> travelerMap = travelerMapper.selectBatchIds(travelerIds).stream()
                .collect(Collectors.toMap(Traveler::getId, e -> e));
        Map<Long, User> userMap = userMapper.selectBatchIds(userIds).stream()
                .collect(Collectors.toMap(User::getId, e -> e));
        for (TravelOrder order : orders) {
            ScenicSpot spot = spotMap.get(order.getSpotId());
            Traveler traveler = travelerMap.get(order.getTravelerId());
            User user = userMap.get(order.getUserId());
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", order.getId());
            item.put("orderNo", order.getOrderNo());
            item.put("userId", order.getUserId());
            item.put("userName", user == null ? "" : (StringUtils.hasText(user.getNickname()) ? user.getNickname() : user.getUsername()));
            item.put("travelerId", order.getTravelerId());
            item.put("travelerName", traveler == null ? "" : traveler.getName());
            item.put("spotId", order.getSpotId());
            item.put("spotName", spot == null ? "" : spot.getName());
            item.put("travelDate", order.getTravelDate());
            item.put("quantity", order.getQuantity());
            item.put("totalAmount", order.getTotalAmount());
            item.put("status", order.getStatus());
            item.put("contactName", order.getContactName());
            item.put("contactPhone", order.getContactPhone());
            item.put("remark", order.getRemark());
            item.put("payTime", order.getPayTime());
            item.put("finishTime", order.getFinishTime());
            item.put("createTime", order.getCreateTime());
            records.add(item);
        }
        return records;
    }

    private LambdaQueryWrapper<TravelOrder> buildQueryWrapper(String orderNo, String status, Long userId) {
        return new LambdaQueryWrapper<TravelOrder>()
                .like(StringUtils.hasText(orderNo), TravelOrder::getOrderNo, orderNo == null ? null : orderNo.trim())
                .eq(StringUtils.hasText(status), TravelOrder::getStatus, status == null ? null : status.trim())
                .eq(userId != null, TravelOrder::getUserId, userId)
                .orderByDesc(TravelOrder::getId);
    }

    private String csvValue(Object value) {
        String str = value == null ? "" : String.valueOf(value);
        str = str.replace("\"", "\"\"");
        return "\"" + str + "\"";
    }

    private String statusText(String status) {
        if (STATUS_WAIT_PAY.equals(status)) {
            return "待支付";
        }
        if (STATUS_PAID.equals(status)) {
            return "已支付";
        }
        if (STATUS_CANCELED.equals(status)) {
            return "已取消";
        }
        if (STATUS_FINISHED.equals(status)) {
            return "已完成";
        }
        return status;
    }
}

