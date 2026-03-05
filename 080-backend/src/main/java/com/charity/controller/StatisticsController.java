package com.charity.controller;

import com.charity.common.Result;
import com.charity.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> getDashboard() {
        Map<String, Object> data = statisticsService.getDashboard();
        return Result.success(data);
    }

    @GetMapping("/donation-trend")
    public Result<Map<String, Object>> getDonationTrend(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Map<String, Object> data = statisticsService.getDonationTrend(startDate, endDate);
        return Result.success(data);
    }

    @GetMapping("/region-distribution")
    public Result<Map<String, Object>> getRegionDistribution() {
        Map<String, Object> data = statisticsService.getRegionDistribution();
        return Result.success(data);
    }
}
