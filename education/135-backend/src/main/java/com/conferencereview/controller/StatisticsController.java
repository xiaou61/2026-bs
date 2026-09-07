package com.conferencereview.controller;

import com.conferencereview.common.Result;
import com.conferencereview.service.AuthService;
import com.conferencereview.service.StatisticsService;
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

