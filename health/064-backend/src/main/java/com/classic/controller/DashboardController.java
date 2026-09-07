package com.classic.controller;

import com.classic.common.Result;
import com.classic.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public Result<?> stats() {
        return Result.success(dashboardService.stats());
    }

    @GetMapping("/order-trend")
    public Result<?> orderTrend() {
        return Result.success(dashboardService.orderTrend());
    }
}
