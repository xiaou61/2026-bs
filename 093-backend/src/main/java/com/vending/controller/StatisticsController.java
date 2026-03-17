package com.vending.controller;

import com.vending.common.Result;
import com.vending.service.StatisticsService;
import com.vending.utils.AuthUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    @GetMapping("/dashboard")
    public Result<?> dashboard(HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(statisticsService.dashboard());
    }

    @GetMapping("/sales-trend")
    public Result<?> salesTrend(@RequestParam(required = false) Integer days, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(statisticsService.salesTrend(days));
    }

    @GetMapping("/hot-products")
    public Result<?> hotProducts(HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(statisticsService.hotProducts());
    }

    @GetMapping("/machine-rank")
    public Result<?> machineRank(HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(statisticsService.machineRank());
    }
}
