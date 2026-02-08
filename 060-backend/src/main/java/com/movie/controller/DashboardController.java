package com.movie.controller;

import com.movie.common.Result;
import com.movie.service.DashboardService;
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
        return Result.success(dashboardService.getStats());
    }

    @GetMapping("/categoryStats")
    public Result<?> categoryStats() {
        return Result.success(dashboardService.getCategoryStats());
    }

    @GetMapping("/orderTrend")
    public Result<?> orderTrend() {
        return Result.success(dashboardService.getOrderTrend());
    }

    @GetMapping("/boxOfficeRank")
    public Result<?> boxOfficeRank() {
        return Result.success(dashboardService.getBoxOfficeRank());
    }
}
