package com.aigccopyright.controller;

import com.aigccopyright.common.Result;
import com.aigccopyright.entity.AuditRule;
import com.aigccopyright.service.AuditRuleService;
import com.aigccopyright.service.AuthService;
import com.aigccopyright.service.OperationLogService;
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
@RequestMapping("/api/rule")
public class RuleController {

    @Autowired
    private AuditRuleService auditRuleService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<AuditRule>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        String keyword,
                                        String ruleType,
                                        Integer status) {
        return Result.success(auditRuleService.page(pageNum, pageSize, keyword, ruleType, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody AuditRule entity) {
        authService.assertAdmin(role);
        auditRuleService.saveEntity(entity);
        operationLogService.record(userId, "审核规则", "新增", "新增规则：" + entity.getRuleName());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody AuditRule entity) {
        authService.assertAdmin(role);
        auditRuleService.saveEntity(entity);
        operationLogService.record(userId, "审核规则", "编辑", "编辑规则：" + entity.getRuleName());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        auditRuleService.removeById(id);
        operationLogService.record(userId, "审核规则", "删除", "删除规则：" + id);
        return Result.success();
    }
}
