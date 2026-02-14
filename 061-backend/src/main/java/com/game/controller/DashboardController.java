package com.game.controller;

import com.game.common.BusinessException;
import com.game.common.Result;
import com.game.service.DashboardService;
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
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(dashboardService.stats());
    }

    @GetMapping("/categoryStats")
    public Result<?> categoryStats(HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(dashboardService.categoryStats());
    }

    @GetMapping("/orderTrend")
    public Result<?> orderTrend(HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(dashboardService.orderTrend());
    }

    @GetMapping("/hotItems")
    public Result<?> hotItems(HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(dashboardService.hotItems());
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
