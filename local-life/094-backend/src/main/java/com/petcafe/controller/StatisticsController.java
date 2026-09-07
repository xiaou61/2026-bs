package com.petcafe.controller;

import com.petcafe.common.Result;
import com.petcafe.service.StatisticsService;
import com.petcafe.utils.AuthUtils;
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

    @GetMapping("/hot-menus")
    public Result<?> hotMenus(HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(statisticsService.hotMenus());
    }

    @GetMapping("/shop-rank")
    public Result<?> shopRank(HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(statisticsService.shopRank());
    }
}
