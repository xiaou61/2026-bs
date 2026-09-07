package com.xiaou.express.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.Result;
import com.xiaou.express.entity.Transaction;
import com.xiaou.express.entity.Wallet;
import com.xiaou.express.service.WalletService;
import com.xiaou.express.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/balance")
    public Result<Wallet> getBalance() {
        Long userId = UserContext.getCurrentUserId();
        Wallet wallet = walletService.getWallet(userId);
        return Result.success(wallet);
    }

    @PostMapping("/recharge")
    public Result<Void> recharge(@RequestBody Map<String, BigDecimal> request) {
        Long userId = UserContext.getCurrentUserId();
        BigDecimal amount = request.get("amount");
        walletService.recharge(userId, amount);
        return Result.success();
    }

    @PostMapping("/withdraw")
    public Result<Void> withdraw(@RequestBody Map<String, BigDecimal> request) {
        Long userId = UserContext.getCurrentUserId();
        BigDecimal amount = request.get("amount");
        walletService.withdraw(userId, amount);
        return Result.success();
    }

    @GetMapping("/transactions")
    public Result<Page<Transaction>> getTransactions(@RequestParam(defaultValue = "1") int pageNum,
                                                       @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = UserContext.getCurrentUserId();
        Page<Transaction> page = walletService.getTransactions(userId, pageNum, pageSize);
        return Result.success(page);
    }
}

