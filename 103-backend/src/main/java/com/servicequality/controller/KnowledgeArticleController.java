package com.servicequality.controller;

import com.servicequality.common.Result;
import com.servicequality.entity.KnowledgeArticle;
import com.servicequality.service.AuthService;
import com.servicequality.service.OperationLogService;
import com.servicequality.service.KnowledgeArticleService;
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
@RequestMapping("/api/article")
public class KnowledgeArticleController {
    @Autowired
    private KnowledgeArticleService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<KnowledgeArticle>> page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, String keyword, Long categoryId, Integer status) {
        return Result.success(service.page(pageNum, pageSize, keyword, categoryId, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody KnowledgeArticle entity) {
        authService.assertStaff(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "知识文章", "新增", "新增知识文章");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody KnowledgeArticle entity) {
        authService.assertStaff(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "知识文章", "编辑", "编辑知识文章：" + entity.getId());
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result<Void> publish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.publish(id);
        operationLogService.record(userId, "知识文章", "发布", "发布知识文章：" + id);
        return Result.success();
    }

    @PutMapping("/offline/{id}")
    public Result<Void> offline(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.offline(id);
        operationLogService.record(userId, "知识文章", "下线", "下线知识文章：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.removeById(id);
        operationLogService.record(userId, "知识文章", "删除", "删除知识文章：" + id);
        return Result.success();
    }
}
