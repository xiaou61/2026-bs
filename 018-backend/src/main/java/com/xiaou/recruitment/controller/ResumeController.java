package com.xiaou.recruitment.controller;

import com.xiaou.recruitment.common.Result;
import com.xiaou.recruitment.entity.Resume;
import com.xiaou.recruitment.service.ApplicationService;
import com.xiaou.recruitment.service.ResumeService;
import com.xiaou.recruitment.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Result<?> createResume(@RequestBody Resume resume, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        var currentUser = userService.getUserById(userId);
        if (currentUser == null || !"student".equals(currentUser.getRole())) {
            return Result.error(403, "仅学生账号可以创建简历");
        }
        resume.setUserId(userId);
        if (resumeService.createResume(resume)) {
            return Result.success(resume);
        }
        return Result.error("创建失败");
    }

    @PutMapping("/update")
    public Result<?> updateResume(@RequestBody Resume resume, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        var currentUser = userService.getUserById(userId);
        if (currentUser == null || !"student".equals(currentUser.getRole())) {
            return Result.error(403, "仅学生账号可以更新简历");
        }
        resume.setUserId(userId);
        if (resumeService.updateResume(resume)) {
            return Result.success(resume);
        }
        return Result.error("更新失败");
    }

    @GetMapping("/my")
    public Result<?> getMyResume(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        var currentUser = userService.getUserById(userId);
        if (currentUser == null || !"student".equals(currentUser.getRole())) {
            return Result.error(403, "仅学生账号可以查看自己的简历");
        }
        Resume resume = resumeService.getResumeByUserId(userId);
        if (resume != null) {
            return Result.success(resume);
        }
        return Result.error("简历不存在");
    }

    @GetMapping("/{id}")
    public Result<?> getResumeById(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        var currentUser = userService.getUserById(userId);
        if (currentUser == null || (!"student".equals(currentUser.getRole()) && !"company".equals(currentUser.getRole()))) {
            return Result.error(403, "无权限查看简历");
        }
        Resume resume = resumeService.getResumeById(id);
        boolean canView = resume != null
                && (("student".equals(currentUser.getRole()) && userId.equals(resume.getUserId()))
                        || ("company".equals(currentUser.getRole())
                                && applicationService.canCompanyViewResume(id, currentUser.getCompanyId())));
        if (canView) {
            return Result.success(resume);
        }
        return Result.error(403, "无权限查看该简历");
    }
}
