package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.OperationLog;
import com.alumni.service.LogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/list")
    public Result<Page<OperationLog>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           String username, String startTime, String endTime) {
        return Result.success(logService.list(pageNum, pageSize, username, startTime, endTime));
    }

    @DeleteMapping("/clear")
    public Result<?> clear() {
        logService.clear();
        return Result.success();
    }
}
