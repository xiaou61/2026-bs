package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.entity.LegalCase;
import com.legalcase.service.AuthService;
import com.legalcase.service.OperationLogService;
import com.legalcase.service.LegalCaseService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/case")
public class LegalCaseController {
    @Autowired
    private LegalCaseService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<PageInfo<LegalCase>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          Long clientId,
                                          Long lawyerId,
                                          String caseType,
                                          Integer status,
                                          String priority) {
        return Result.success(service.page(pageNum, pageSize, keyword, clientId, lawyerId, caseType, status, priority));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody LegalCase legalCase) {
        authService.assertStaff(role);
        service.saveEntity(legalCase);
        operationLogService.record(userId, "案件台账", "新增", "新增案件台账");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody LegalCase legalCase) {
        authService.assertStaff(role);
        service.saveEntity(legalCase);
        operationLogService.record(userId, "案件台账", "编辑", "编辑案件台账：" + legalCase.getId());
        return Result.success();
    }

    @PutMapping("/advance/{id}")
    public Result<Void> advance(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.advance(id);
        operationLogService.record(userId, "案件台账", "推进", "推进案件：" + id);
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.close(id);
        operationLogService.record(userId, "案件台账", "结案", "结案案件：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.delete(id);
        operationLogService.record(userId, "案件台账", "删除", "删除案件台账：" + id);
        return Result.success();
    }
}
