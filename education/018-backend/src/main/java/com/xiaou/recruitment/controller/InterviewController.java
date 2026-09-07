package com.xiaou.recruitment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.recruitment.common.Result;
import com.xiaou.recruitment.entity.Interview;
import com.xiaou.recruitment.service.InterviewService;
import com.xiaou.recruitment.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Result<?> createInterview(@RequestBody Interview interview, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        var currentUser = userService.getUserById(userId);
        if (currentUser == null || !"company".equals(currentUser.getRole()) || currentUser.getCompanyId() == null) {
            return Result.error(403, "仅企业账号可以创建面试");
        }
        if (interviewService.createInterview(interview, currentUser.getCompanyId())) {
            return Result.success(interview);
        }
        return Result.error("创建失败");
    }

    @PutMapping("/update")
    public Result<?> updateInterview(@RequestBody Interview interview, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        var currentUser = userService.getUserById(userId);
        if (currentUser == null || !"company".equals(currentUser.getRole()) || currentUser.getCompanyId() == null) {
            return Result.error(403, "仅企业账号可以更新面试");
        }
        if (interviewService.updateInterview(interview, currentUser.getCompanyId())) {
            return Result.success(interview);
        }
        return Result.error("更新失败");
    }

    @GetMapping("/my")
    public Result<?> getMyInterviews(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        var currentUser = userService.getUserById(userId);
        if (currentUser == null || !"student".equals(currentUser.getRole())) {
            return Result.error(403, "仅学生账号可以查看我的面试");
        }
        IPage<Interview> interviewPage = interviewService.getMyInterviews(page, size, userId);
        return Result.success(interviewPage);
    }

    @GetMapping("/company")
    public Result<?> getCompanyInterviews(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        var currentUser = userService.getUserById(userId);
        if (currentUser == null || !"company".equals(currentUser.getRole()) || currentUser.getCompanyId() == null) {
            return Result.error(403, "仅企业账号可以查看企业面试");
        }
        IPage<Interview> interviewPage = interviewService.getCompanyInterviews(page, size, currentUser.getCompanyId());
        return Result.success(interviewPage);
    }

    @PutMapping("/cancel/{id}")
    public Result<?> cancelInterview(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        var currentUser = userService.getUserById(userId);
        if (currentUser != null && interviewService.cancelInterview(id, userId, currentUser.getCompanyId(),
                currentUser.getRole())) {
            return Result.success();
        }
        return Result.error("取消失败");
    }
}
