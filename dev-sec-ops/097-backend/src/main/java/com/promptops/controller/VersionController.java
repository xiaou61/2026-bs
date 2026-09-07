package com.promptops.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.promptops.common.Result;
import com.promptops.entity.PromptVersion;
import com.promptops.service.AuthService;
import com.promptops.service.OperationLogService;
import com.promptops.service.PromptVersionService;
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
@RequestMapping("/api/version")
public class VersionController {

    @Autowired
    private PromptVersionService promptVersionService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<PromptVersion>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            Long assetId,
                                            Integer status) {
        return Result.success(promptVersionService.page(pageNum, pageSize, assetId, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody PromptVersion entity) {
        authService.assertEngineer(role);
        promptVersionService.saveEntity(entity, userId);
        operationLogService.record(userId, "Prompt版本", "新增", "新增版本：" + entity.getVersionNo());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody PromptVersion entity) {
        authService.assertEngineer(role);
        promptVersionService.saveEntity(entity, userId);
        operationLogService.record(userId, "Prompt版本", "编辑", "编辑版本：" + entity.getVersionNo());
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result<Void> publish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEngineer(role);
        promptVersionService.publish(id);
        operationLogService.record(userId, "Prompt版本", "发布", "发布版本：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEngineer(role);
        promptVersionService.removeById(id);
        operationLogService.record(userId, "Prompt版本", "删除", "删除版本：" + id);
        return Result.success();
    }
}
