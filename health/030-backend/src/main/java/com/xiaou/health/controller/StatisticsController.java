package com.xiaou.health.controller;

import com.xiaou.health.common.Constants;
import com.xiaou.health.common.Result;
import com.xiaou.health.service.StatisticsService;
import com.xiaou.health.util.UserContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/admin")
    public Result<Map<String, Object>> getAdminStatistics() {
        try {
            if (!UserContext.hasRole(Constants.ROLE_ADMIN)) {
                return Result.error(403, "仅管理员可查看统计分析");
            }
            return Result.success(statisticsService.getAdminStatistics());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/doctor")
    public Result<Map<String, Object>> getDoctorStatistics() {
        try {
            if (!UserContext.hasRole(Constants.ROLE_DOCTOR)) {
                return Result.error(403, "仅医生可查看统计分析");
            }
            return Result.success(statisticsService.getDoctorStatistics(UserContext.getUserId()));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
