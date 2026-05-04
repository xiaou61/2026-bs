package com.promptops.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.promptops.common.Result;
import com.promptops.entity.ModelConfig;
import com.promptops.service.AuthService;
import com.promptops.service.ModelConfigService;
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
@RequestMapping("/api/model")
public class ModelController {

    @Autowired
    private ModelConfigService modelConfigService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<ModelConfig>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          Integer status) {
        return Result.success(modelConfigService.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ModelConfig entity) {
        authService.assertAdmin(role);
        modelConfigService.saveEntity(entity);
        operationLogService.record(userId, "模型配置", "新增", "新增模型：" + entity.getName());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ModelConfig entity) {
        authService.assertAdmin(role);
        modelConfigService.saveEntity(entity);
        operationLogService.record(userId, "模型配置", "编辑", "编辑模型：" + entity.getName());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        modelConfigService.removeById(id);
        operationLogService.record(userId, "模型配置", "删除", "删除模型：" + id);
        return Result.success();
    }
}
