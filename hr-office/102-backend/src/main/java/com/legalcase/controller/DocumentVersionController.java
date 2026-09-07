package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.entity.DocumentVersion;
import com.legalcase.service.AuthService;
import com.legalcase.service.OperationLogService;
import com.legalcase.service.DocumentVersionService;
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
@RequestMapping("/api/version")
public class DocumentVersionController {
    @Autowired
    private DocumentVersionService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<PageInfo<DocumentVersion>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          Long documentId) {
        return Result.success(service.page(pageNum, pageSize, keyword, documentId));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody DocumentVersion documentVersion) {
        authService.assertStaff(role);
        service.saveEntity(documentVersion);
        operationLogService.record(userId, "文书版本", "新增", "新增文书版本");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody DocumentVersion documentVersion) {
        authService.assertStaff(role);
        service.saveEntity(documentVersion);
        operationLogService.record(userId, "文书版本", "编辑", "编辑文书版本：" + documentVersion.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.delete(id);
        operationLogService.record(userId, "文书版本", "删除", "删除文书版本：" + id);
        return Result.success();
    }
}
