package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.common.BusinessException;
import com.recruitmatch.entity.JobPosition;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.OperationLogService;
import com.recruitmatch.service.JobPositionService;
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

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/job")
public class JobController {
    @Autowired
    private JobPositionService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<JobPosition>> page(@RequestAttribute String role,
                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      String keyword,
                                      String jobType,
                                      Integer status) {
        authService.assertHrOnly(role);
        LambdaQueryWrapper<JobPosition> wrapper = new LambdaQueryWrapper<>(); wrapper.and(org.springframework.util.StringUtils.hasText(keyword), item -> item.like(JobPosition::getJobName, keyword).or().like(JobPosition::getDepartment, keyword).or().like(JobPosition::getDescription, keyword)); wrapper.eq(org.springframework.util.StringUtils.hasText(jobType), JobPosition::getJobType, jobType); wrapper.eq(status != null, JobPosition::getStatus, status); wrapper.orderByDesc(JobPosition::getId); return Result.success(service.page(new Page<>(pageNum, pageSize), wrapper));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody JobPosition entity) {
        authService.assertHrOnly(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "岗位管理", "新增", "新增岗位管理");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody JobPosition entity) {
        authService.assertHrOnly(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "岗位管理", "编辑", "编辑岗位管理：" + entity.getId());
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result<Void> publish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHrOnly(role);
        JobPosition entity = service.getById(id);
        if (entity == null) {
            throw new BusinessException(400, "岗位不存在");
        }
        entity.setStatus(1);
        entity.setUpdateTime(LocalDateTime.now());
        service.updateById(entity);
        operationLogService.record(userId, "岗位管理", "发布", "发布岗位：" + id);
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHrOnly(role);
        JobPosition entity = service.getById(id);
        if (entity == null) {
            throw new BusinessException(400, "岗位不存在");
        }
        entity.setStatus(2);
        entity.setUpdateTime(LocalDateTime.now());
        service.updateById(entity);
        operationLogService.record(userId, "岗位管理", "关闭", "关闭岗位：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHrOnly(role);
        service.removeById(id);
        operationLogService.record(userId, "岗位管理", "删除", "删除岗位管理：" + id);
        return Result.success();
    }
}
