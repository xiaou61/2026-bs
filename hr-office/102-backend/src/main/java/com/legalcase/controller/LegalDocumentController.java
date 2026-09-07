package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.entity.LegalDocument;
import com.legalcase.service.AuthService;
import com.legalcase.service.OperationLogService;
import com.legalcase.service.LegalDocumentService;
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
@RequestMapping("/api/document")
public class LegalDocumentController {
    @Autowired
    private LegalDocumentService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<PageInfo<LegalDocument>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          Long caseId,
                                          Long templateId,
                                          String documentType,
                                          Integer status) {
        return Result.success(service.page(pageNum, pageSize, keyword, caseId, templateId, documentType, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody LegalDocument legalDocument) {
        authService.assertStaff(role);
        service.saveEntity(legalDocument);
        operationLogService.record(userId, "法律文书", "新增", "新增法律文书");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody LegalDocument legalDocument) {
        authService.assertStaff(role);
        service.saveEntity(legalDocument);
        operationLogService.record(userId, "法律文书", "编辑", "编辑法律文书：" + legalDocument.getId());
        return Result.success();
    }

    @PutMapping("/generate/{id}")
    public Result<Void> generate(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.generate(id, userId);
        operationLogService.record(userId, "法律文书", "生成", "生成文书：" + id);
        return Result.success();
    }

    @PutMapping("/review")
    public Result<Void> review(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody LegalDocument entity) {
        authService.assertStaff(role);
        service.review(entity);
        operationLogService.record(userId, "法律文书", "复核", "复核文书：" + entity.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.delete(id);
        operationLogService.record(userId, "法律文书", "删除", "删除法律文书：" + id);
        return Result.success();
    }
}
