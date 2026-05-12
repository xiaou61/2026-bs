package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private AuthService authService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard(@RequestAttribute String role) {
        authService.assertAdmin(role);
        return Result.success(statisticsService.dashboard());
    }
}
