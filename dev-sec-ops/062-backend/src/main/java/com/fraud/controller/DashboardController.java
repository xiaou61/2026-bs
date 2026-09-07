package com.fraud.controller;

import com.fraud.common.BusinessException;
import com.fraud.common.Result;
import com.fraud.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public Result<?> stats(HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        return Result.success(dashboardService.stats());
    }

    @GetMapping("/riskTrend")
    public Result<?> riskTrend(HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        return Result.success(dashboardService.riskTrend());
    }

    @GetMapping("/riskLevelDist")
    public Result<?> riskLevelDist(HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        return Result.success(dashboardService.riskLevelDist());
    }

    @GetMapping("/topRules")
    public Result<?> topRules(HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        return Result.success(dashboardService.topRules());
    }

    @GetMapping("/topUsers")
    public Result<?> topUsers(HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        return Result.success(dashboardService.topUsers());
    }

    private void checkRiskRole(String role) {
        if (!"ADMIN".equals(role) && !"ANALYST".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
