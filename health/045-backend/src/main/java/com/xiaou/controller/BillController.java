package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.Bill;
import com.xiaou.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Tag(name = "费用管理")
@RestController
@RequestMapping("/api/bill")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @Operation(summary = "账单列表")
    @GetMapping("/list")
    public Result<IPage<Bill>> list(@RequestParam(required = false) Long elderId,
                                    @RequestParam(defaultValue = "1") Integer current,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(required = false) Integer status) {
        return Result.success(billService.pageList(elderId, current, size, status));
    }

    @Operation(summary = "账单详情")
    @GetMapping("/{id}")
    public Result<Bill> detail(@PathVariable Long id) {
        return Result.success(billService.getById(id));
    }

    @Operation(summary = "缴费")
    @PostMapping("/pay")
    public Result<Void> pay(@AuthenticationPrincipal Long userId,
                            @RequestParam Long billId,
                            @RequestParam BigDecimal amount,
                            @RequestParam(defaultValue = "1") Integer payMethod) {
        billService.pay(billId, amount, payMethod, userId);
        return Result.success();
    }
}
