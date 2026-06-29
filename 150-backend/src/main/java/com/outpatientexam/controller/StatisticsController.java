package com.outpatientexam.controller;

import com.outpatientexam.common.Result;
import com.outpatientexam.service.AuthService;
import com.outpatientexam.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController extends BaseController {
    private final StatisticsService statisticsService;

    public StatisticsController(AuthService authService, StatisticsService statisticsService) {
        super(authService);
        this.statisticsService = statisticsService;
    }

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard(@RequestAttribute String role) {
        checkAuthenticated(role);
        return Result.success(statisticsService.dashboard());
    }
}
