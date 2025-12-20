package com.xiaou.wedding.controller;

import com.xiaou.wedding.common.Result;
import com.xiaou.wedding.entity.Order;
import com.xiaou.wedding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public Result<List<Order>> list(@RequestParam(required = false) Long customerId,
                                    @RequestParam(required = false) String status) {
        return Result.success(orderService.list(customerId, status));
    }

    @GetMapping("/{id}")
    public Result<Order> detail(@PathVariable Long id) {
        return Result.success(orderService.detail(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody Order order) {
        orderService.create(order);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id,
                                     @RequestParam String status,
                                     @RequestParam(required = false) BigDecimal paidAmount) {
        orderService.updateStatus(id, status, paidAmount);
        return Result.success();
    }
}
