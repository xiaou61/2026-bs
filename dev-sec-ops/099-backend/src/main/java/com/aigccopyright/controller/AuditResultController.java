package com.aigccopyright.controller;

import com.aigccopyright.common.Result;
import com.aigccopyright.entity.AuditResult;
import com.aigccopyright.service.AuditResultService;
import com.aigccopyright.service.AuthService;
import com.aigccopyright.service.OperationLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/result")
public class AuditResultController {

    @Autowired
    private AuditResultService auditResultService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<AuditResult>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          Long taskId,
                                          String riskLevel,
                                          Integer reviewStatus) {
        return Result.success(auditResultService.page(pageNum, pageSize, taskId, riskLevel, reviewStatus));
    }

    @PutMapping("/review")
    public Result<Void> review(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody AuditResult entity) {
        authService.assertAuditor(role);
        auditResultService.review(entity);
        operationLogService.record(userId, "审核结果", "复核", "复核审核结果：" + entity.getId());
        return Result.success();
    }
}
