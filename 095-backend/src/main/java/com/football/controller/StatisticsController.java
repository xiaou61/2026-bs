package com.football.controller;

import com.football.common.Result;
import com.football.service.StatisticsService;
import com.football.utils.AuthUtils;
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
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(statisticsService.dashboard());
    }

    @GetMapping("/match-trend")
    public Result<?> matchTrend(@RequestParam(required = false) Integer days, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(statisticsService.matchTrend(days));
    }

    @GetMapping("/standing-rank")
    public Result<?> standingRank(@RequestParam(required = false) Long seasonId, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(statisticsService.standingRank(seasonId));
    }

    @GetMapping("/goal-rank")
    public Result<?> goalRank(HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(statisticsService.goalRank());
    }
}
