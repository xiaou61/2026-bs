package com.promptops.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.promptops.common.Result;
import com.promptops.entity.PromptCategory;
import com.promptops.service.AuthService;
import com.promptops.service.OperationLogService;
import com.promptops.service.PromptCategoryService;
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
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private PromptCategoryService promptCategoryService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<PromptCategory>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             String keyword,
                                             Integer status) {
        return Result.success(promptCategoryService.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody PromptCategory entity) {
        authService.assertAdmin(role);
        promptCategoryService.saveEntity(entity);
        operationLogService.record(userId, "Prompt分类", "新增", "新增分类：" + entity.getName());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody PromptCategory entity) {
        authService.assertAdmin(role);
        promptCategoryService.saveEntity(entity);
        operationLogService.record(userId, "Prompt分类", "编辑", "编辑分类：" + entity.getName());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        promptCategoryService.removeById(id);
        operationLogService.record(userId, "Prompt分类", "删除", "删除分类：" + id);
        return Result.success();
    }
}
