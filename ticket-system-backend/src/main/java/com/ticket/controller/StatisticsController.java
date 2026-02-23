package com.ticket.controller;

import com.ticket.common.Result;
import com.ticket.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> getDashboardStats() {
        return Result.success(statisticsService.getDashboardStats());
    }

    @GetMapping("/sales-trend")
    public Result<List<Map<String, Object>>> getSalesTrend(@RequestParam(defaultValue = "7") Integer days) {
        return Result.success(statisticsService.getSalesTrend(days));
    }

    @GetMapping("/box-office")
    public Result<List<Map<String, Object>>> getBoxOfficeRank() {
        return Result.success(statisticsService.getBoxOfficeRank());
    }
}
