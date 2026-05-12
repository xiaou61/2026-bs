package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.entity.EvidenceMaterial;
import com.legalcase.service.AuthService;
import com.legalcase.service.OperationLogService;
import com.legalcase.service.EvidenceMaterialService;
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

import java.util.Map;
@RestController
@RequestMapping("/api/evidence")
public class EvidenceMaterialController {
    @Autowired
    private EvidenceMaterialService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<PageInfo<EvidenceMaterial>> page(@RequestAttribute Long userId,
                                          @RequestAttribute String role,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          Long caseId,
                                          Integer verifyStatus) {
        authService.assertClient(role);
        return Result.success(service.pageByRole(pageNum, pageSize, keyword, caseId, verifyStatus, userId, role));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody EvidenceMaterial evidenceMaterial) {
        authService.assertStaff(role);
        service.saveEntity(evidenceMaterial);
        operationLogService.record(userId, "证据材料", "新增", "新增证据材料");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody EvidenceMaterial evidenceMaterial) {
        authService.assertStaff(role);
        service.saveEntity(evidenceMaterial);
        operationLogService.record(userId, "证据材料", "编辑", "编辑证据材料：" + evidenceMaterial.getId());
        return Result.success();
    }

    @PutMapping("/verify/{id}")
    public Result<Void> verify(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id, @RequestBody Map<String, String> body) {
        authService.assertStaff(role);
        service.verify(id, body.get("comment"));
        operationLogService.record(userId, "证据材料", "核验", "核验证据：" + id);
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id, @RequestBody Map<String, String> body) {
        authService.assertStaff(role);
        service.reject(id, body.get("comment"));
        operationLogService.record(userId, "证据材料", "驳回", "驳回证据：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStaff(role);
        service.delete(id);
        operationLogService.record(userId, "证据材料", "删除", "删除证据材料：" + id);
        return Result.success();
    }
}
