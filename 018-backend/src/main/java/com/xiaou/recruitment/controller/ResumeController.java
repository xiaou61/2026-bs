package com.xiaou.recruitment.controller;

import com.xiaou.recruitment.common.Result;
import com.xiaou.recruitment.entity.Resume;
import com.xiaou.recruitment.service.ResumeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/create")
    public Result<?> createResume(@RequestBody Resume resume, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        resume.setUserId(userId);
        if (resumeService.createResume(resume)) {
            return Result.success(resume);
        }
        return Result.error("创建失败");
    }

    @PutMapping("/update")
    public Result<?> updateResume(@RequestBody Resume resume, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        resume.setUserId(userId);
        if (resumeService.updateResume(resume)) {
            return Result.success(resume);
        }
        return Result.error("更新失败");
    }

    @GetMapping("/my")
    public Result<?> getMyResume(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Resume resume = resumeService.getResumeByUserId(userId);
        if (resume != null) {
            return Result.success(resume);
        }
        return Result.error("简历不存在");
    }

    @GetMapping("/{id}")
    public Result<?> getResumeById(@PathVariable Long id) {
        Resume resume = resumeService.getResumeById(id);
        if (resume != null) {
            return Result.success(resume);
        }
        return Result.error("简历不存在");
    }
}
