package com.xiaou.ordering.controller;

import com.xiaou.ordering.common.Result;
import com.xiaou.ordering.entity.Orders;
import com.xiaou.ordering.service.MerchantManageService;
import com.xiaou.ordering.service.OrderService;
import com.xiaou.ordering.util.UserContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/merchant")
public class MerchantManageController {

    private final MerchantManageService merchantManageService;
    private final OrderService orderService;

    public MerchantManageController(MerchantManageService merchantManageService, OrderService orderService) {
        this.merchantManageService = merchantManageService;
        this.orderService = orderService;
    }

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> getDashboard() {
        return Result.success(merchantManageService.getDashboard(UserContext.getCurrentUserId()));
    }

    @GetMapping("/orders")
    public Result<List<Orders>> getOrders(@RequestParam(required = false) Integer status) {
        return Result.success(orderService.getMerchantOrders(UserContext.getCurrentUserId(), status));
    }

    @PostMapping("/orders/{id}/accept")
    public Result<Void> acceptOrder(@PathVariable Long id) {
        orderService.acceptOrder(UserContext.getCurrentUserId(), id);
        return Result.success();
    }

    @PostMapping("/orders/{id}/ready")
    public Result<Void> readyOrder(@PathVariable Long id) {
        orderService.readyOrder(UserContext.getCurrentUserId(), id);
        return Result.success();
    }
}
