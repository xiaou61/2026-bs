package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.service.AlertRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/alert")
public class AlertRecordController {

    @Resource
    private AlertRecordService alertRecordService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long equipmentId,
                          @RequestParam(required = false) String alertLevel,
                          @RequestParam(required = false) Integer status) {
        return Result.success(alertRecordService.page(pageNum, pageSize, equipmentId, alertLevel, status));
    }

    @PutMapping("/handle/{id}")
    public Result<?> handle(@PathVariable Long id) {
        alertRecordService.handle(id);
        return Result.success();
    }

    @GetMapping("/stats")
    public Result<?> stats() {
        return Result.success(alertRecordService.stats());
    }
}
