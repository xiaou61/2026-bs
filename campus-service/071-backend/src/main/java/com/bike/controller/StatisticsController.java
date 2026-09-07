package com.bike.controller;

import com.bike.common.Result;
import com.bike.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/overview")
    public Result<?> getOverview() {
        return Result.success(statisticsService.getOverview());
    }

    @GetMapping("/ride-trend")
    public Result<?> getRideTrend(@RequestParam(defaultValue = "7") Integer days) {
        return Result.success(statisticsService.getRideTrend(days));
    }

    @GetMapping("/income-trend")
    public Result<?> getIncomeTrend(@RequestParam(defaultValue = "7") Integer days) {
        return Result.success(statisticsService.getIncomeTrend(days));
    }

    @GetMapping("/station-rank")
    public Result<?> getStationRank() {
        return Result.success(statisticsService.getStationRank());
    }

    @GetMapping("/bike-type-ratio")
    public Result<?> getBikeTypeRatio() {
        return Result.success(statisticsService.getBikeTypeRatio());
    }
}
