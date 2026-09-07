package com.promptops.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.promptops.common.Result;
import com.promptops.entity.EvaluationTask;
import com.promptops.service.AuthService;
import com.promptops.service.EvaluationTaskService;
import com.promptops.service.OperationLogService;
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
@RequestMapping("/api/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationTaskService evaluationTaskService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<EvaluationTask>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             String keyword,
                                             Integer status) {
        return Result.success(evaluationTaskService.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody EvaluationTask entity) {
        authService.assertEngineer(role);
        evaluationTaskService.saveEntity(entity, userId);
        operationLogService.record(userId, "评测任务", "新增", "新增任务：" + entity.getName());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody EvaluationTask entity) {
        authService.assertEngineer(role);
        evaluationTaskService.saveEntity(entity, userId);
        operationLogService.record(userId, "评测任务", "编辑", "编辑任务：" + entity.getName());
        return Result.success();
    }

    @PutMapping("/run/{id}")
    public Result<Void> run(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEngineer(role);
        evaluationTaskService.run(id);
        operationLogService.record(userId, "评测任务", "启动", "启动评测任务：" + id);
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEngineer(role);
        evaluationTaskService.finish(id);
        operationLogService.record(userId, "评测任务", "完成", "完成评测任务：" + id);
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    public Result<Void> cancel(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEngineer(role);
        evaluationTaskService.cancel(id);
        operationLogService.record(userId, "评测任务", "取消", "取消评测任务：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEngineer(role);
        evaluationTaskService.removeById(id);
        operationLogService.record(userId, "评测任务", "删除", "删除评测任务：" + id);
        return Result.success();
    }
}
