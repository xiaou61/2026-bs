package com.xiaou.campusshare.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.entity.BalanceRecord;
import com.xiaou.campusshare.entity.DepositRecord;
import com.xiaou.campusshare.service.DepositRecordService;
import com.xiaou.campusshare.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DepositRecordService depositRecordService;

    @PostMapping("/payment/deposit/charge")
    public Result<?> chargeDeposit(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Long userId = (Long) request.getAttribute("userId");
        BigDecimal amount = new BigDecimal(params.get("amount"));
        boolean success = paymentService.chargeDeposit(userId, amount);
        if (success) {
            return Result.success("充值成功");
        } else {
            return Result.error("充值失败");
        }
    }

    @PostMapping("/payment/balance/charge")
    public Result<?> chargeBalance(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Long userId = (Long) request.getAttribute("userId");
        BigDecimal amount = new BigDecimal(params.get("amount"));
        boolean success = paymentService.chargeBalance(userId, amount);
        if (success) {
            return Result.success("充值成功");
        } else {
            return Result.error("充值失败");
        }
    }

    @GetMapping("/deposit/record")
    public Result<List<DepositRecord>> getDepositRecord(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        LambdaQueryWrapper<DepositRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DepositRecord::getUserId, userId);
        wrapper.orderByDesc(DepositRecord::getCreateTime);
        List<DepositRecord> records = depositRecordService.list(wrapper);
        return Result.success(records);
    }

    @GetMapping("/balance/record")
    public Result<List<BalanceRecord>> getBalanceRecord(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        LambdaQueryWrapper<BalanceRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BalanceRecord::getUserId, userId);
        wrapper.orderByDesc(BalanceRecord::getCreateTime);
        List<BalanceRecord> records = paymentService.list(wrapper);
        return Result.success(records);
    }
}

