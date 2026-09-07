package com.repair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.Result;
import com.repair.entity.FundRecord;
import com.repair.service.FundRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/fund")
public class FundRecordController {

    @Autowired
    private FundRecordService fundRecordService;

    @GetMapping("/list")
    public Result<Page<FundRecord>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "10") int pageSize,
                                            @RequestParam(required = false) String recordType,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Page<FundRecord> page = fundRecordService.getList(pageNum, pageSize, recordType, startDate, endDate);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody FundRecord fundRecord) {
        fundRecordService.add(fundRecord);
        return Result.success();
    }
}

