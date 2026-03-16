package com.railway.controller;

import com.railway.common.Result;
import com.railway.service.StatisticsService;
import com.railway.utils.AuthUtils;
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
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        return Result.success(statisticsService.dashboard());
    }

    @GetMapping("/sales-trend")
    public Result<?> salesTrend(@RequestParam(required = false) Integer days, HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        return Result.success(statisticsService.salesTrend(days));
    }

    @GetMapping("/hot-train")
    public Result<?> hotTrain(HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        return Result.success(statisticsService.hotTrain());
    }
}
