package com.servicequality.controller;

import com.servicequality.common.Result;
import com.servicequality.entity.QualityTask;
import com.servicequality.service.AuthService;
import com.servicequality.service.OperationLogService;
import com.servicequality.service.QualityTaskService;
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
@RequestMapping("/api/quality-task")
public class QualityTaskController {
    @Autowired
    private QualityTaskService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<QualityTask>> page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, String keyword, Long orderId, Integer status, String priority) {
        return Result.success(service.page(pageNum, pageSize, keyword, orderId, status, priority));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody QualityTask entity) {
        authService.assertQa(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "质检任务", "新增", "新增质检任务");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody QualityTask entity) {
        authService.assertQa(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "质检任务", "编辑", "编辑质检任务：" + entity.getId());
        return Result.success();
    }

    @PutMapping("/run/{id}")
    public Result<Void> run(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertQa(role);
        service.run(id);
        operationLogService.record(userId, "质检任务", "启动", "启动质检任务：" + id);
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertQa(role);
        service.finish(id);
        operationLogService.record(userId, "质检任务", "完成", "完成质检任务：" + id);
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertQa(role);
        service.reject(id);
        operationLogService.record(userId, "质检任务", "驳回", "驳回质检任务：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertQa(role);
        service.removeById(id);
        operationLogService.record(userId, "质检任务", "删除", "删除质检任务：" + id);
        return Result.success();
    }
}
