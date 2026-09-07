package com.property.controller;

import com.property.common.BusinessException;
import com.property.common.Result;
import com.property.service.DashboardService;
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
        checkStaffRole((String) request.getAttribute("role"));
        return Result.success(dashboardService.stats());
    }

    @GetMapping("/trend")
    public Result<?> trend(HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        return Result.success(dashboardService.trend());
    }

    private void checkStaffRole(String role) {
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
