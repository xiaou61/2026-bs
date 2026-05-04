package com.servicequality.controller;

import com.servicequality.common.Result;
import com.servicequality.entity.RecommendRecord;
import com.servicequality.service.AuthService;
import com.servicequality.service.OperationLogService;
import com.servicequality.service.RecommendRecordService;
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
@RequestMapping("/api/recommend")
public class RecommendRecordController {
    @Autowired
    private RecommendRecordService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<RecommendRecord>> page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, String keyword, Long orderId, Long agentId, Integer adoptStatus) {
        return Result.success(service.page(pageNum, pageSize, keyword, orderId, agentId, adoptStatus));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody RecommendRecord entity) {
        authService.assertStaff(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "知识推荐", "新增", "新增知识推荐");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody RecommendRecord entity) {
        authService.assertStaff(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "知识推荐", "编辑", "编辑知识推荐：" + entity.getId());
        return Result.success();
    }

    @PutMapping("/adopt/{id}")
    public Result<Void> adopt(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.adopt(id);
        operationLogService.record(userId, "知识推荐", "采纳", "采纳推荐：" + id);
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.reject(id);
        operationLogService.record(userId, "知识推荐", "不采纳", "不采纳推荐：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.removeById(id);
        operationLogService.record(userId, "知识推荐", "删除", "删除知识推荐：" + id);
        return Result.success();
    }
}
