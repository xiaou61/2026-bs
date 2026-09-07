package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.Order;
import com.xiaou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Result<?> create(@RequestBody Map<String, Object> params, @RequestAttribute Long userId) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) params.get("items");
        @SuppressWarnings("unchecked")
        Map<String, String> shippingInfo = (Map<String, String>) params.get("shippingInfo");

        Order order = orderService.createOrder(userId, items, shippingInfo);
        return Result.success(order);
    }

    @GetMapping("/my-orders")
    public Result<?> myOrders(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer size,
                              @RequestAttribute Long userId,
                              @RequestAttribute String userRole) {
        IPage<Order> result;
        if ("buyer".equals(userRole)) {
            result = orderService.getOrdersByBuyer(userId, page, size);
        } else if ("farmer".equals(userRole)) {
            result = orderService.getOrdersByFarmer(userId, page, size);
        } else {
            result = orderService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page, size));
        }
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id, @RequestAttribute Long userId) {
        Map<String, Object> orderDetail = orderService.getOrderDetail(id, userId);
        return Result.success(orderDetail);
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id,
                                   @RequestBody Map<String, String> params,
                                   @RequestAttribute Long userId) {
        String status = params.get("status");
        orderService.updateOrderStatus(id, status, userId);
        return Result.success("状态更新成功");
    }
}

