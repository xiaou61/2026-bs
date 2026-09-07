package com.enrollment.controller;

import com.enrollment.common.Result;
import com.enrollment.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/dashboard")
    public Result<?> getDashboard() {
        return Result.success(statsService.getDashboard());
    }

    @GetMapping("/application")
    public Result<?> getApplicationStats() {
        return Result.success(statsService.getApplicationStats());
    }

    @GetMapping("/admission")
    public Result<?> getAdmissionStats() {
        return Result.success(statsService.getAdmissionStats());
    }

    @GetMapping("/major")
    public Result<?> getMajorStats() {
        return Result.success(statsService.getMajorStats());
    }

    @GetMapping("/province")
    public Result<?> getProvinceStats() {
        return Result.success(statsService.getProvinceStats());
    }
}
