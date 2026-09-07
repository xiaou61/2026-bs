package com.xiaou.campusshare.controller;

import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.entity.Withdrawal;
import com.xiaou.campusshare.service.WithdrawalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @PostMapping("/apply")
    public Result<?> apply(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Long userId = (Long) request.getAttribute("userId");
        try {
            BigDecimal amount = new BigDecimal(params.get("amount"));
            Withdrawal withdrawal = withdrawalService.apply(
                    userId,
                    amount,
                    params.get("withdrawType"),
                    params.get("accountName"),
                    params.get("accountNo")
            );
            return Result.success(withdrawal);
        } catch (IllegalArgumentException ex) {
            return Result.error(ex.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<Withdrawal>> getList(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(withdrawalService.getUserWithdrawals(userId));
    }
}
