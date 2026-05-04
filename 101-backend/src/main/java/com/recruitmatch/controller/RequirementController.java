package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.entity.JobRequirement;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.OperationLogService;
import com.recruitmatch.service.JobRequirementService;
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
@RequestMapping("/api/requirement")
public class RequirementController {
    @Autowired
    private JobRequirementService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<JobRequirement>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      String keyword,
                                      Long jobId,
                                      Integer status) {
        LambdaQueryWrapper<JobRequirement> wrapper = new LambdaQueryWrapper<>(); wrapper.and(org.springframework.util.StringUtils.hasText(keyword), item -> item.like(JobRequirement::getKeyword, keyword).or().like(JobRequirement::getDescription, keyword)); wrapper.eq(jobId != null, JobRequirement::getJobId, jobId); wrapper.eq(status != null, JobRequirement::getStatus, status); wrapper.orderByDesc(JobRequirement::getId); return Result.success(service.page(new Page<>(pageNum, pageSize), wrapper));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody JobRequirement entity) {
        authService.assertHr(role);
        service.save(entity);
        operationLogService.record(userId, "岗位要求", "新增", "新增岗位要求");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody JobRequirement entity) {
        authService.assertHr(role);
        service.updateById(entity);
        operationLogService.record(userId, "岗位要求", "编辑", "编辑岗位要求：" + entity.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHr(role);
        service.removeById(id);
        operationLogService.record(userId, "岗位要求", "删除", "删除岗位要求：" + id);
        return Result.success();
    }
}
