package com.xiaou.recruitment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.recruitment.common.Result;
import com.xiaou.recruitment.entity.Application;
import com.xiaou.recruitment.service.ApplicationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/submit")
    public Result<?> submitApplication(@RequestBody Application application, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        application.setUserId(userId);
        if (applicationService.submitApplication(application)) {
            return Result.success(application);
        }
        return Result.error("投递失败");
    }

    @GetMapping("/my")
    public Result<?> getMyApplications(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        IPage<Application> applicationPage = applicationService.getMyApplications(page, size, userId);
        return Result.success(applicationPage);
    }

    @GetMapping("/received")
    public Result<?> getReceivedApplications(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam Long jobId) {
        IPage<Application> applicationPage = applicationService.getReceivedApplications(page, size, jobId);
        return Result.success(applicationPage);
    }

    @PutMapping("/updateStatus")
    public Result<?> updateApplicationStatus(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        String status = params.get("status").toString();
        String remark = params.get("remark") != null ? params.get("remark").toString() : null;
        if (applicationService.updateApplicationStatus(id, status, remark)) {
            return Result.success();
        }
        return Result.error("更新失败");
    }
}
