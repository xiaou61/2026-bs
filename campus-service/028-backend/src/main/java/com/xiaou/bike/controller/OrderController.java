package com.xiaou.bike.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.bike.common.Result;
import com.xiaou.bike.entity.RentalOrder;
import com.xiaou.bike.service.RentalOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final RentalOrderService rentalOrderService;

    /**
     * 获取用户订单列表
     */
    @GetMapping("/list")
    public Result<Page<RentalOrder>> getUserOrders(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status) {
        Page<RentalOrder> result = rentalOrderService.getUserOrders(page, size, status);
        return Result.success(result);
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/{orderId}")
    public Result<RentalOrder> getOrderDetail(@PathVariable Long orderId) {
        RentalOrder order = rentalOrderService.getOrderById(orderId);
        return Result.success(order);
    }
}
