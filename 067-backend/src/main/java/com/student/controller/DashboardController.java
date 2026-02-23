package com.student.controller;

import com.student.common.BusinessException;
import com.student.common.Result;
import com.student.service.DashboardService;
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
        checkStaff((String) request.getAttribute("role"));
        return Result.success(dashboardService.stats());
    }

    @GetMapping("/trend")
    public Result<?> trend(HttpServletRequest request) {
        checkStaff((String) request.getAttribute("role"));
        return Result.success(dashboardService.trend());
    }

    private void checkStaff(String role) {
        if (!"ADMIN".equals(role) && !"TEACHER".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
