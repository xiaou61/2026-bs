package com.hospital.controller;

import com.hospital.common.Result;
import com.hospital.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard(@RequestAttribute("userId") Long userId,
                                                 @RequestAttribute("role") String role) {
        return Result.success(statisticsService.dashboard(userId, role));
    }

    @GetMapping("/department-rank")
    public Result<List<Map<String, Object>>> departmentRank(@RequestAttribute("role") String role) {
        return Result.success(statisticsService.departmentRank(role));
    }

    @GetMapping("/appointment-trend")
    public Result<List<Map<String, Object>>> appointmentTrend(@RequestAttribute("role") String role) {
        return Result.success(statisticsService.appointmentTrend(role));
    }

    @GetMapping("/hot-doctor")
    public Result<List<Map<String, Object>>> hotDoctor() {
        return Result.success(statisticsService.hotDoctorRank());
    }
}
