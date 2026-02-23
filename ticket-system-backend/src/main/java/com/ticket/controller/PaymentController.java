package com.ticket.controller;

import com.ticket.common.Result;
import com.ticket.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public Result<Void> createPayment(@RequestParam Long orderId, @RequestParam String payType) {
        paymentService.createPayment(orderId, payType);
        return Result.success();
    }

    @PostMapping("/balance")
    public Result<Void> balancePay(@RequestParam Long orderId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        paymentService.balancePay(orderId, userId);
        return Result.success();
    }

    @PostMapping("/recharge")
    public Result<Void> recharge(@RequestParam BigDecimal amount, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        paymentService.recharge(userId, amount);
        return Result.success();
    }
}
