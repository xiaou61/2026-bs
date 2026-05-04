package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.entity.CertificateRecord;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.CertificateRecordService;
import com.recruitmatch.service.OperationLogService;
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

import java.util.Map;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {
    @Autowired
    private CertificateRecordService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<CertificateRecord>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                String keyword,
                                                Long candidateId,
                                                Integer verifyStatus) {
        return Result.success(service.page(pageNum, pageSize, keyword, candidateId, verifyStatus));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody CertificateRecord entity) {
        authService.assertCandidate(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "证书材料", "新增", "新增证书：" + entity.getCertName());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody CertificateRecord entity) {
        authService.assertCandidate(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "证书材料", "编辑", "编辑证书：" + entity.getCertName());
        return Result.success();
    }

    @PutMapping("/verify/{id}")
    public Result<Void> verify(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id, @RequestBody(required = false) Map<String, String> body) {
        authService.assertHr(role);
        service.verify(id, body == null ? "核验通过" : body.get("comment"));
        operationLogService.record(userId, "证书材料", "核验", "核验证书：" + id);
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id, @RequestBody(required = false) Map<String, String> body) {
        authService.assertHr(role);
        service.reject(id, body == null ? "核验驳回" : body.get("comment"));
        operationLogService.record(userId, "证书材料", "驳回", "驳回证书：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertCandidate(role);
        service.removeById(id);
        operationLogService.record(userId, "证书材料", "删除", "删除证书：" + id);
        return Result.success();
    }
}
