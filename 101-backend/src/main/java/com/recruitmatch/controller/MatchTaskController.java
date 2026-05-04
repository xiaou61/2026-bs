package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.entity.MatchTask;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.MatchTaskService;
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
@RequestMapping("/api/match-task")
public class MatchTaskController {
    @Autowired
    private MatchTaskService service;
    @Autowired
    private AuthService authService;
    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<MatchTask>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        String keyword,
                                        Integer status,
                                        String priority) {
        return Result.success(service.page(pageNum, pageSize, keyword, status, priority));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody MatchTask entity) {
        authService.assertHr(role);
        service.saveEntity(entity, userId);
        operationLogService.record(userId, "匹配任务", "新增", "新增匹配任务：" + entity.getTaskName());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody MatchTask entity) {
        authService.assertHr(role);
        service.saveEntity(entity, userId);
        operationLogService.record(userId, "匹配任务", "编辑", "编辑匹配任务：" + entity.getTaskName());
        return Result.success();
    }

    @PutMapping("/run/{id}")
    public Result<Void> run(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHr(role);
        service.run(id);
        operationLogService.record(userId, "匹配任务", "启动", "启动匹配任务：" + id);
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHr(role);
        service.finish(id);
        operationLogService.record(userId, "匹配任务", "完成", "完成匹配任务：" + id);
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHr(role);
        service.reject(id);
        operationLogService.record(userId, "匹配任务", "驳回", "驳回匹配任务：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertHr(role);
        service.removeById(id);
        operationLogService.record(userId, "匹配任务", "删除", "删除匹配任务：" + id);
        return Result.success();
    }
}
