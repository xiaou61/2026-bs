package com.charity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charity.common.Result;
import com.charity.entity.FundRecord;
import com.charity.service.FundRecordService;
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
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                            @RequestAttribute("userId") String userId) {
        Page<FundRecord> page = fundRecordService.getList(pageNum, pageSize, recordType, startDate, endDate, Long.parseLong(userId));
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody FundRecord fundRecord,
                              @RequestAttribute("userId") String userId) {
        fundRecordService.add(fundRecord, Long.parseLong(userId));
        return Result.success();
    }
}
