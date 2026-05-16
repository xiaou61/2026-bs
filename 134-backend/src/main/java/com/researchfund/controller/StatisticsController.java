package com.researchfund.controller;

import com.researchfund.common.Result;
import com.researchfund.service.AuthService;
import com.researchfund.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final AuthService authService;
    private final StatisticsService statisticsService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard(@RequestAttribute String role) {
        authService.assertAuthenticated(role);
        return Result.success(statisticsService.dashboard());
    }
}
