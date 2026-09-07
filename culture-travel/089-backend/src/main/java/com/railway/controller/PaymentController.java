package com.railway.controller;

import com.railway.common.Result;
import com.railway.dto.PaymentDTO;
import com.railway.dto.RechargeDTO;
import com.railway.service.PaymentService;
import com.railway.utils.AuthUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping("/create")
    public Result<?> create(@RequestBody PaymentDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(paymentService.create(userId, dto));
    }

    @PostMapping("/balance")
    public Result<?> balance(@RequestBody PaymentDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(paymentService.balancePay(userId, dto.getOrderId()));
    }

    @PostMapping("/recharge")
    public Result<?> recharge(@RequestBody RechargeDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        paymentService.recharge(userId, dto.getAmount());
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String status,
                          HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        return Result.success(paymentService.page(pageNum, pageSize, status));
    }
}
