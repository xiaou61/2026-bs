package com.promptops.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.promptops.common.Result;
import com.promptops.entity.ScoreRule;
import com.promptops.service.AuthService;
import com.promptops.service.OperationLogService;
import com.promptops.service.ScoreRuleService;
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
@RequestMapping("/api/rule")
public class ScoreRuleController {

    @Autowired
    private ScoreRuleService scoreRuleService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<ScoreRule>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        String keyword,
                                        Integer status) {
        return Result.success(scoreRuleService.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ScoreRule entity) {
        authService.assertAdmin(role);
        scoreRuleService.saveEntity(entity);
        operationLogService.record(userId, "评分规则", "新增", "新增规则：" + entity.getName());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ScoreRule entity) {
        authService.assertAdmin(role);
        scoreRuleService.saveEntity(entity);
        operationLogService.record(userId, "评分规则", "编辑", "编辑规则：" + entity.getName());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        scoreRuleService.removeById(id);
        operationLogService.record(userId, "评分规则", "删除", "删除规则：" + id);
        return Result.success();
    }
}
