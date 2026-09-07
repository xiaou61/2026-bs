package com.fraud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.common.Result;
import com.fraud.entity.RiskRule;
import com.fraud.service.OperationLogService;
import com.fraud.service.RiskRuleService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/rule")
public class RuleController {

    @Resource
    private RiskRuleService riskRuleService;

    @Resource
    private OperationLogService operationLogService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(riskRuleService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String ruleName,
                          @RequestParam(required = false) String ruleType,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        Page<RiskRule> page = riskRuleService.page(pageNum, pageSize, ruleName, ruleType, status);
        return Result.success(page);
    }

    @PostMapping
    public Result<?> add(@RequestBody RiskRule rule, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        riskRuleService.save(rule);
        operationLogService.add("RULE", "ADD", (Long) request.getAttribute("userId"), (String) request.getAttribute("role"), rule.getRuleCode(), "新增规则");
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody RiskRule rule, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        riskRuleService.save(rule);
        operationLogService.add("RULE", "UPDATE", (Long) request.getAttribute("userId"), (String) request.getAttribute("role"), String.valueOf(rule.getId()), "更新规则");
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        riskRuleService.deleteById(id);
        operationLogService.add("RULE", "DELETE", (Long) request.getAttribute("userId"), (String) request.getAttribute("role"), String.valueOf(id), "删除规则");
        return Result.success();
    }

    private void checkRiskRole(String role) {
        if (!"ADMIN".equals(role) && !"ANALYST".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
