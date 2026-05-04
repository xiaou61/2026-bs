package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.entity.DocumentTemplate;
import com.legalcase.service.AuthService;
import com.legalcase.service.OperationLogService;
import com.legalcase.service.DocumentTemplateService;
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
@RequestMapping("/api/template")
public class DocumentTemplateController {
    @Autowired
    private DocumentTemplateService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<PageInfo<DocumentTemplate>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          String templateType,
                                          Integer status) {
        return Result.success(service.page(pageNum, pageSize, keyword, templateType, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody DocumentTemplate documentTemplate) {
        authService.assertStaff(role);
        service.saveEntity(documentTemplate);
        operationLogService.record(userId, "文书模板", "新增", "新增文书模板");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody DocumentTemplate documentTemplate) {
        authService.assertStaff(role);
        service.saveEntity(documentTemplate);
        operationLogService.record(userId, "文书模板", "编辑", "编辑文书模板：" + documentTemplate.getId());
        return Result.success();
    }

    @PutMapping("/enable/{id}")
    public Result<Void> enable(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.enable(id);
        operationLogService.record(userId, "文书模板", "启用", "启用模板：" + id);
        return Result.success();
    }

    @PutMapping("/disable/{id}")
    public Result<Void> disable(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.disable(id);
        operationLogService.record(userId, "文书模板", "停用", "停用模板：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.delete(id);
        operationLogService.record(userId, "文书模板", "删除", "删除文书模板：" + id);
        return Result.success();
    }
}
