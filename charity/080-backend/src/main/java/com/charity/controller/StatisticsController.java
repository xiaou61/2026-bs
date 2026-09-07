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
    public Result<Map<String, Object>> getDashboard(@RequestAttribute("userId") String userId) {
        Map<String, Object> data = statisticsService.getDashboard(Long.parseLong(userId));
        return Result.success(data);
    }

    @GetMapping("/donation-trend")
    public Result<Map<String, Object>> getDonationTrend(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                        @RequestAttribute("userId") String userId) {
        Map<String, Object> data = statisticsService.getDonationTrend(startDate, endDate, Long.parseLong(userId));
        return Result.success(data);
    }

    @GetMapping("/region-distribution")
    public Result<Map<String, Object>> getRegionDistribution(@RequestAttribute("userId") String userId) {
        Map<String, Object> data = statisticsService.getRegionDistribution(Long.parseLong(userId));
        return Result.success(data);
    }
}
