package com.xiaou.recruitment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.recruitment.common.Result;
import com.xiaou.recruitment.entity.Interview;
import com.xiaou.recruitment.service.InterviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @PostMapping("/create")
    public Result<?> createInterview(@RequestBody Interview interview, HttpServletRequest request) {
        if (interviewService.createInterview(interview)) {
            return Result.success(interview);
        }
        return Result.error("创建失败");
    }

    @PutMapping("/update")
    public Result<?> updateInterview(@RequestBody Interview interview, HttpServletRequest request) {
        if (interviewService.updateInterview(interview)) {
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
        IPage<Interview> interviewPage = interviewService.getMyInterviews(page, size, userId);
        return Result.success(interviewPage);
    }

    @PutMapping("/cancel/{id}")
    public Result<?> cancelInterview(@PathVariable Long id) {
        if (interviewService.cancelInterview(id)) {
            return Result.success();
        }
        return Result.error("取消失败");
    }
}
