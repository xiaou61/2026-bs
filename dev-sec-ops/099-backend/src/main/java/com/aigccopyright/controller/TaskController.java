package com.aigccopyright.controller;

import com.aigccopyright.common.Result;
import com.aigccopyright.entity.AuditTask;
import com.aigccopyright.service.AuditTaskService;
import com.aigccopyright.service.AuthService;
import com.aigccopyright.service.OperationLogService;
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
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private AuditTaskService auditTaskService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<AuditTask>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        String keyword,
                                        Integer status,
                                        String priority) {
        return Result.success(auditTaskService.page(pageNum, pageSize, keyword, status, priority));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody AuditTask entity) {
        authService.assertAuditor(role);
        auditTaskService.saveEntity(entity, userId);
        operationLogService.record(userId, "审核任务", "新增", "新增审核任务：" + entity.getTaskName());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody AuditTask entity) {
        authService.assertAuditor(role);
        auditTaskService.saveEntity(entity, userId);
        operationLogService.record(userId, "审核任务", "编辑", "编辑审核任务：" + entity.getTaskName());
        return Result.success();
    }

    @PutMapping("/run/{id}")
    public Result<Void> run(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAuditor(role);
        auditTaskService.run(id);
        operationLogService.record(userId, "审核任务", "启动", "启动审核任务：" + id);
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAuditor(role);
        auditTaskService.finish(id);
        operationLogService.record(userId, "审核任务", "完成", "完成审核任务：" + id);
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAuditor(role);
        auditTaskService.reject(id);
        operationLogService.record(userId, "审核任务", "退回", "退回审核任务：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAuditor(role);
        auditTaskService.removeById(id);
        operationLogService.record(userId, "审核任务", "删除", "删除审核任务：" + id);
        return Result.success();
    }
}
