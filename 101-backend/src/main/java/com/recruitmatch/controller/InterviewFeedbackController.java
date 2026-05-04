package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.entity.InterviewFeedback;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.OperationLogService;
import com.recruitmatch.service.InterviewFeedbackService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
public class InterviewFeedbackController {
    @Autowired
    private InterviewFeedbackService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<InterviewFeedback>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      String keyword,
                                      Long planId,
                                      Integer resultStatus) {
        return Result.success(service.page(pageNum, pageSize, keyword, planId, resultStatus));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody InterviewFeedback entity) {
        authService.assertInterviewer(role);
        service.save(entity);
        operationLogService.record(userId, "面试反馈", "新增", "新增面试反馈");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody InterviewFeedback entity) {
        authService.assertInterviewer(role);
        service.updateById(entity);
        operationLogService.record(userId, "面试反馈", "编辑", "编辑面试反馈：" + entity.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertInterviewer(role);
        service.removeById(id);
        operationLogService.record(userId, "面试反馈", "删除", "删除面试反馈：" + id);
        return Result.success();
    }
}
