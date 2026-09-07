package com.promptops.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.promptops.common.Result;
import com.promptops.entity.PromptAsset;
import com.promptops.service.AuthService;
import com.promptops.service.OperationLogService;
import com.promptops.service.PromptAssetService;
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
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    private PromptAssetService promptAssetService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<PromptAsset>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          Long categoryId,
                                          Integer status) {
        return Result.success(promptAssetService.page(pageNum, pageSize, keyword, categoryId, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody PromptAsset entity) {
        authService.assertEngineer(role);
        promptAssetService.saveEntity(entity, userId);
        operationLogService.record(userId, "Prompt资产", "新增", "新增资产：" + entity.getTitle());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody PromptAsset entity) {
        authService.assertEngineer(role);
        promptAssetService.saveEntity(entity, userId);
        operationLogService.record(userId, "Prompt资产", "编辑", "编辑资产：" + entity.getTitle());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEngineer(role);
        promptAssetService.removeById(id);
        operationLogService.record(userId, "Prompt资产", "删除", "删除资产：" + id);
        return Result.success();
    }
}
