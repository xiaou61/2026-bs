package com.promptops.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.promptops.common.Result;
import com.promptops.entity.PromptTestCase;
import com.promptops.service.AuthService;
import com.promptops.service.OperationLogService;
import com.promptops.service.PromptTestCaseService;
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
@RequestMapping("/api/case")
public class CaseController {

    @Autowired
    private PromptTestCaseService promptTestCaseService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<PromptTestCase>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             Long assetId,
                                             String difficulty,
                                             Integer status) {
        return Result.success(promptTestCaseService.page(pageNum, pageSize, assetId, difficulty, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody PromptTestCase entity) {
        authService.assertEngineer(role);
        promptTestCaseService.saveEntity(entity, userId);
        operationLogService.record(userId, "测试用例", "新增", "新增用例：" + entity.getTitle());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody PromptTestCase entity) {
        authService.assertEngineer(role);
        promptTestCaseService.saveEntity(entity, userId);
        operationLogService.record(userId, "测试用例", "编辑", "编辑用例：" + entity.getTitle());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEngineer(role);
        promptTestCaseService.removeById(id);
        operationLogService.record(userId, "测试用例", "删除", "删除用例：" + id);
        return Result.success();
    }
}
