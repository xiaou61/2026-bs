package com.xiaou.recruitment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.recruitment.common.Result;
import com.xiaou.recruitment.entity.Application;
import com.xiaou.recruitment.service.ApplicationService;
import com.xiaou.recruitment.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @PostMapping("/submit")
    public Result<?> submitApplication(@RequestBody Application application, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        var currentUser = userService.getUserById(userId);
        if (currentUser == null || !"student".equals(currentUser.getRole())) {
            return Result.error(403, "仅学生账号可以投递简历");
        }
        if (applicationService.submitApplication(application, userId)) {
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
        var currentUser = userService.getUserById(userId);
        if (currentUser == null || !"student".equals(currentUser.getRole())) {
            return Result.error(403, "仅学生账号可以查看我的投递");
        }
        IPage<Application> applicationPage = applicationService.getMyApplications(page, size, userId);
        return Result.success(applicationPage);
    }

    @GetMapping("/received")
    public Result<?> getReceivedApplications(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long jobId,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        var currentUser = userService.getUserById(userId);
        if (currentUser == null || !"company".equals(currentUser.getRole()) || currentUser.getCompanyId() == null) {
            return Result.error(403, "仅企业账号可以查看收到的简历");
        }
        IPage<Application> applicationPage = applicationService.getReceivedApplications(page, size,
                currentUser.getCompanyId(), jobId);
        return Result.success(applicationPage);
    }

    @PutMapping("/updateStatus")
    public Result<?> updateApplicationStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        var currentUser = userService.getUserById(userId);
        if (currentUser == null || !"company".equals(currentUser.getRole()) || currentUser.getCompanyId() == null) {
            return Result.error(403, "仅企业账号可以更新投递状态");
        }
        Long id = Long.parseLong(params.get("id").toString());
        String status = params.get("status").toString();
        String remark = params.get("remark") != null ? params.get("remark").toString() : null;
        if (applicationService.updateApplicationStatus(id, status, remark, currentUser.getCompanyId())) {
            return Result.success();
        }
        return Result.error("更新失败");
    }
}
