package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.entity.ParsingTask;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.OperationLogService;
import com.recruitmatch.service.ParsingTaskService;
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
@RequestMapping("/api/parse-task")
public class ParsingTaskController {
    @Autowired
    private ParsingTaskService service;
    @Autowired
    private AuthService authService;
    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<ParsingTask>> page(@RequestAttribute String role,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          Integer status,
                                          String priority) {
        authService.assertHrOnly(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status, priority));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ParsingTask entity) {
        authService.assertHrOnly(role);
        service.saveEntity(entity, userId);
        operationLogService.record(userId, "解析任务", "新增", "新增解析任务：" + entity.getTaskName());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ParsingTask entity) {
        authService.assertHrOnly(role);
        service.saveEntity(entity, userId);
        operationLogService.record(userId, "解析任务", "编辑", "编辑解析任务：" + entity.getTaskName());
        return Result.success();
    }

    @PutMapping("/run/{id}")
    public Result<Void> run(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHrOnly(role);
        service.run(id);
        operationLogService.record(userId, "解析任务", "启动", "启动解析任务：" + id);
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHrOnly(role);
        service.finish(id);
        operationLogService.record(userId, "解析任务", "完成", "完成解析任务：" + id);
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHrOnly(role);
        service.reject(id);
        operationLogService.record(userId, "解析任务", "驳回", "驳回解析任务：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHrOnly(role);
        service.removeById(id);
        operationLogService.record(userId, "解析任务", "删除", "删除解析任务：" + id);
        return Result.success();
    }
}
