package com.hospital.controller;

import com.github.pagehelper.PageInfo;
import com.hospital.common.Result;
import com.hospital.entity.PaymentOrder;
import com.hospital.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    public Result<PageInfo<PaymentOrder>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(required = false) String keyword,
                                               @RequestParam(required = false) Integer status,
                                               @RequestAttribute("role") String role) {
        return Result.success(orderService.page(keyword, status, pageNum, pageSize, role));
    }

    @GetMapping("/my")
    public Result<PageInfo<PaymentOrder>> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false) Integer status,
                                                 @RequestAttribute("userId") Long userId) {
        return Result.success(orderService.myPage(userId, status, pageNum, pageSize));
    }

    @PutMapping("/pay/{id}")
    public Result<String> pay(@PathVariable Long id,
                              @RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role) {
        orderService.pay(id, userId, role);
        return Result.success();
    }
}
