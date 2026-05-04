package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.entity.InterviewPlan;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.InterviewPlanService;
import com.recruitmatch.service.OperationLogService;
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
@RequestMapping("/api/interview")
public class InterviewPlanController {
    @Autowired
    private InterviewPlanService service;
    @Autowired
    private AuthService authService;
    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<InterviewPlan>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            Long candidateId,
                                            Long jobId,
                                            Integer status,
                                            String interviewType) {
        return Result.success(service.page(pageNum, pageSize, candidateId, jobId, status, interviewType));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody InterviewPlan entity) {
        authService.assertHr(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "面试排期", "新增", "新增面试计划：" + entity.getCandidateId());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody InterviewPlan entity) {
        authService.assertHr(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "面试排期", "编辑", "编辑面试计划：" + entity.getId());
        return Result.success();
    }

    @PutMapping("/confirm/{id}")
    public Result<Void> confirm(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertInterviewer(role);
        service.updateStatus(id, 1);
        operationLogService.record(userId, "面试排期", "确认", "确认面试：" + id);
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    public Result<Void> cancel(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHr(role);
        service.updateStatus(id, 2);
        operationLogService.record(userId, "面试排期", "取消", "取消面试：" + id);
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertInterviewer(role);
        service.updateStatus(id, 3);
        operationLogService.record(userId, "面试排期", "完成", "完成面试：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHr(role);
        service.removeById(id);
        operationLogService.record(userId, "面试排期", "删除", "删除面试：" + id);
        return Result.success();
    }
}
