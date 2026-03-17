package com.vending.controller;

import com.vending.common.Result;
import com.vending.dto.PaymentDTO;
import com.vending.dto.RechargeDTO;
import com.vending.service.PaymentService;
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

    @PostMapping("/recharge")
    public Result<?> recharge(@RequestBody RechargeDTO dto, HttpServletRequest request) {
        paymentService.recharge((Long) request.getAttribute("userId"), dto);
        return Result.success();
    }

    @PostMapping("/balance")
    public Result<?> balancePay(@RequestBody PaymentDTO dto, HttpServletRequest request) {
        paymentService.balancePay((Long) request.getAttribute("userId"), dto);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String payType,
                          @RequestParam(required = false) String status,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(paymentService.page(pageNum, pageSize, payType, status, userId, role));
    }
}
