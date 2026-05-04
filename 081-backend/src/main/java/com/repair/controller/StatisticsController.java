package com.repair.controller;

import com.repair.common.Result;
import com.repair.service.StatisticsService;
import com.repair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> getDashboard(@RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        return Result.success(statisticsService.getDashboard());
    }

    @GetMapping("/order-trend")
    public Result<Map<String, Object>> getOrderTrend(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                      @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        return Result.success(statisticsService.getOrderTrend(startDate, endDate));
    }
}
