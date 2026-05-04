package com.aigccopyright.controller;

import com.aigccopyright.common.Result;
import com.aigccopyright.entity.CopyrightRegister;
import com.aigccopyright.service.AuthService;
import com.aigccopyright.service.CopyrightRegisterService;
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

import java.util.Map;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    private CopyrightRegisterService copyrightRegisterService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<CopyrightRegister>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                Long assetId,
                                                Integer registerStatus) {
        return Result.success(copyrightRegisterService.page(pageNum, pageSize, assetId, registerStatus));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody CopyrightRegister entity) {
        authService.assertCreator(role);
        copyrightRegisterService.saveEntity(entity);
        operationLogService.record(userId, "版权登记", "新增", "新增版权登记：" + entity.getAssetId());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody CopyrightRegister entity) {
        authService.assertCreator(role);
        copyrightRegisterService.saveEntity(entity);
        operationLogService.record(userId, "版权登记", "编辑", "编辑版权登记：" + entity.getId());
        return Result.success();
    }

    @PutMapping("/approve/{id}")
    public Result<Void> approve(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id, @RequestBody(required = false) Map<String, String> body) {
        authService.assertAuditor(role);
        copyrightRegisterService.approve(id, body == null ? "登记通过" : body.get("comment"));
        operationLogService.record(userId, "版权登记", "通过", "通过版权登记：" + id);
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id, @RequestBody(required = false) Map<String, String> body) {
        authService.assertAuditor(role);
        copyrightRegisterService.reject(id, body == null ? "登记驳回" : body.get("comment"));
        operationLogService.record(userId, "版权登记", "驳回", "驳回版权登记：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertCreator(role);
        copyrightRegisterService.removeById(id);
        operationLogService.record(userId, "版权登记", "删除", "删除版权登记：" + id);
        return Result.success();
    }
}
