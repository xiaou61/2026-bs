package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.entity.MaintenancePlan;
import com.mfg.service.MaintenancePlanService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/maintenancePlan")
public class MaintenancePlanController {

    @Resource
    private MaintenancePlanService maintenancePlanService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long equipmentId,
                          @RequestParam(required = false) String status) {
        return Result.success(maintenancePlanService.page(pageNum, pageSize, equipmentId, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody MaintenancePlan plan) {
        maintenancePlanService.add(plan);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody MaintenancePlan plan) {
        maintenancePlanService.update(plan);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        maintenancePlanService.delete(id);
        return Result.success();
    }
}
