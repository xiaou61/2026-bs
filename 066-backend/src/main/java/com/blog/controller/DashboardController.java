package com.blog.controller;

import com.blog.common.BusinessException;
import com.blog.common.Result;
import com.blog.service.DashboardService;
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

    @GetMapping("/trend")
    public Result<?> trend(HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(dashboardService.trend());
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
