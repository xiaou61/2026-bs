package com.xiaou.bike.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.bike.common.Result;
import com.xiaou.bike.dto.RechargeDTO;
import com.xiaou.bike.entity.RechargeRecord;
import com.xiaou.bike.entity.UserWallet;
import com.xiaou.bike.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 钱包控制器
 */
@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    /**
     * 获取钱包信息
     */
    @GetMapping("/balance")
    public Result<UserWallet> getBalance() {
        UserWallet wallet = walletService.getWallet();
        return Result.success(wallet);
    }

    /**
     * 充值
     */
    @PostMapping("/recharge")
    public Result<Void> recharge(@RequestBody @Valid RechargeDTO dto) {
        walletService.recharge(dto);
        return Result.success();
    }

    /**
     * 申请退还押金
     */
    @PostMapping("/refund-deposit")
    public Result<Void> refundDeposit() {
        walletService.refundDeposit();
        return Result.success();
    }

    /**
     * 获取充值记录
     */
    @GetMapping("/recharge-records")
    public Result<Page<RechargeRecord>> getRechargeRecords(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<RechargeRecord> result = walletService.getRechargeRecords(page, size);
        return Result.success(result);
    }
}
