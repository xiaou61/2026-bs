package com.xiaou.hair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.hair.common.Result;
import com.xiaou.hair.entity.Order;
import com.xiaou.hair.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     */
    @GetMapping("/list")
    public Result<Page<Order>> getOrderList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer payStatus) {
        Page<Order> page = orderService.getOrderList(pageNum, pageSize, payStatus);
        return Result.success(page);
    }

    /**
     * 订单详情
     */
    @GetMapping("/{id}")
    public Result<Order> getOrderDetail(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return Result.success(order);
    }

    /**
     * 订单支付
     */
    @PostMapping("/{id}/pay")
    public Result<Void> payOrder(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String payType = params.getOrDefault("payType", "余额支付");
        orderService.payOrder(id, payType);
        return Result.success("支付成功");
    }
}
