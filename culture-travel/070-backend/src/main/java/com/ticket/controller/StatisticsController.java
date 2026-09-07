package com.ticket.controller;

import com.ticket.common.Result;
import com.ticket.service.StatisticsService;
import com.ticket.utils.AuthUtils;
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
    public Result<?> salesTrend(@RequestParam(defaultValue = "7") Integer days, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(statisticsService.salesTrend(days));
    }

    @GetMapping("/box-office")
    public Result<?> boxOffice(HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(statisticsService.boxOffice());
    }
}
