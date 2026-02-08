package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    @GetMapping("/equipmentStatus")
    public Result<?> equipmentStatus() {
        return Result.success(dashboardService.equipmentStatus());
    }

    @GetMapping("/orderStats")
    public Result<?> orderStats() {
        return Result.success(dashboardService.orderStats());
    }

    @GetMapping("/alertStats")
    public Result<?> alertStats() {
        return Result.success(dashboardService.alertStats());
    }

    @GetMapping("/qualityTrend")
    public Result<?> qualityTrend() {
        return Result.success(dashboardService.qualityTrend());
    }

    @GetMapping("/overview")
    public Result<?> overview() {
        return Result.success(dashboardService.overview());
    }
}
