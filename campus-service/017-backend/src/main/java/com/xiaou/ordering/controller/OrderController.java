package com.xiaou.ordering.controller;

import com.xiaou.ordering.common.Result;
import com.xiaou.ordering.dto.OrderCreateRequest;
import com.xiaou.ordering.entity.Orders;
import com.xiaou.ordering.service.OrderService;
import com.xiaou.ordering.util.UserContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Result<Orders> createOrder(@RequestBody OrderCreateRequest request) {
        return Result.success(orderService.createOrder(UserContext.getCurrentUserId(), request));
    }

    @GetMapping
    public Result<List<Orders>> getOrders(@RequestParam(required = false) Integer status) {
        return Result.success(orderService.getUserOrders(UserContext.getCurrentUserId(), status));
    }

    @GetMapping("/{id}")
    public Result<Map<String, Object>> getOrderDetail(@PathVariable Long id) {
        return Result.success(orderService.getUserOrderDetail(UserContext.getCurrentUserId(), id));
    }

    @PostMapping("/{id}/cancel")
    public Result<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelUserOrder(UserContext.getCurrentUserId(), id);
        return Result.success();
    }

    @PostMapping("/{id}/confirm")
    public Result<Void> confirmPickup(@PathVariable Long id) {
        orderService.confirmPickup(UserContext.getCurrentUserId(), id);
        return Result.success();
    }
}
