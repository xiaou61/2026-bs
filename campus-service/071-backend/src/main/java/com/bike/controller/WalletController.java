package com.bike.controller;

import com.bike.common.Result;
import com.bike.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/recharge")
    public Result<?> recharge(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute("userId");
        BigDecimal amount = new BigDecimal(params.get("amount").toString());
        walletService.recharge(userId, amount);
        return Result.success();
    }

    @GetMapping("/records")
    public Result<?> getRecords(HttpServletRequest request,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(walletService.getRecords(userId, pageNum, pageSize));
    }

    @PostMapping("/deposit")
    public Result<?> payDeposit(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        walletService.payDeposit(userId);
        return Result.success();
    }

    @PostMapping("/deposit/refund")
    public Result<?> refundDeposit(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        walletService.refundDeposit(userId);
        return Result.success();
    }
}
