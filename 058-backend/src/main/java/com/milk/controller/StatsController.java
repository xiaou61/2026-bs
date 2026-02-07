package com.milk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.milk.common.Result;
import com.milk.mapper.MilkProductMapper;
import com.milk.mapper.SubscriptionMapper;
import com.milk.mapper.UserMapper;
import com.milk.service.DeliveryRecordService;
import com.milk.service.MilkOrderService;
import com.milk.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MilkProductMapper milkProductMapper;

    @Resource
    private SubscriptionMapper subscriptionMapper;

    @Resource
    private MilkOrderService milkOrderService;

    @Resource
    private DeliveryRecordService deliveryRecordService;

    @Resource
    private PaymentService paymentService;

    @GetMapping("/dashboard")
    public Result<?> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("userCount", userMapper.selectCount(new QueryWrapper<>()));
        data.put("productCount", milkProductMapper.selectCount(new QueryWrapper<>()));
        data.put("subscriptionCount", subscriptionMapper.selectCount(new QueryWrapper<>()));
        data.put("orderCount", milkOrderService.countAll());
        data.put("pendingDelivery", milkOrderService.countByStatus(0));
        data.put("completedOrder", milkOrderService.countByStatus(2));
        data.put("pendingComplaint", deliveryRecordService.countByStatus(0));
        data.put("totalAmount", paymentService.totalAmount());
        return Result.success(data);
    }

    @GetMapping("/order")
    public Result<?> orderStats() {
        Map<String, Object> data = new HashMap<>();
        data.put("pending", milkOrderService.countByStatus(0));
        data.put("delivering", milkOrderService.countByStatus(1));
        data.put("completed", milkOrderService.countByStatus(2));
        data.put("cancelled", milkOrderService.countByStatus(3));
        data.put("total", milkOrderService.countAll());
        return Result.success(data);
    }

    @GetMapping("/delivery")
    public Result<?> deliveryStats() {
        Map<String, Object> data = new HashMap<>();
        data.put("pending", deliveryRecordService.countByStatus(0));
        data.put("completed", deliveryRecordService.countByStatus(1));
        data.put("exception", deliveryRecordService.countByStatus(2));
        return Result.success(data);
    }
}
