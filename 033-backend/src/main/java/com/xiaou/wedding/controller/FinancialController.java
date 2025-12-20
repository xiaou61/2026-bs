package com.xiaou.wedding.controller;

import com.xiaou.wedding.common.Result;
import com.xiaou.wedding.entity.FinancialRecord;
import com.xiaou.wedding.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/financial")
public class FinancialController {

    @Autowired
    private FinancialService financialService;

    @GetMapping("/list")
    public Result<List<FinancialRecord>> list(@RequestParam(required = false) String recordType,
                                              @RequestParam(required = false) String date) {
        return Result.success(financialService.list(recordType, date));
    }

    @PostMapping
    public Result<Void> create(@RequestBody FinancialRecord record) {
        financialService.create(record);
        return Result.success();
    }

    @GetMapping("/summary")
    public Result<String> summary() {
        BigDecimal income = financialService.incomeTotal();
        BigDecimal expense = financialService.expenseTotal();
        return Result.success("ok", String.format("income:%s,expense:%s", income, expense));
    }
}
