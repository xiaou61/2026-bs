package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.entity.SensorData;
import com.mfg.service.SensorDataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/sensorData")
public class SensorDataController {

    @Resource
    private SensorDataService sensorDataService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long equipmentId) {
        return Result.success(sensorDataService.page(pageNum, pageSize, equipmentId));
    }

    @PostMapping
    public Result<?> add(@RequestBody SensorData sensorData) {
        sensorDataService.add(sensorData);
        return Result.success();
    }

    @GetMapping("/latest/{equipmentId}")
    public Result<?> latest(@PathVariable Long equipmentId) {
        return Result.success(sensorDataService.getLatest(equipmentId));
    }

    @GetMapping("/trend/{equipmentId}")
    public Result<?> trend(@PathVariable Long equipmentId,
                           @RequestParam(required = false) Integer limit) {
        return Result.success(sensorDataService.getTrend(equipmentId, limit));
    }
}
