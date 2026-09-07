package com.repair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.Result;
import com.repair.entity.GrowthRecord;
import com.repair.service.GrowthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/growth")
public class GrowthRecordController {

    @Autowired
    private GrowthRecordService growthRecordService;

    @GetMapping("/list")
    public Result<Page<GrowthRecord>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "10") int pageSize,
                                              @RequestParam(required = false) Long childId) {
        Page<GrowthRecord> page = growthRecordService.getList(pageNum, pageSize, childId);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody GrowthRecord growthRecord) {
        growthRecordService.add(growthRecord);
        return Result.success();
    }
}

