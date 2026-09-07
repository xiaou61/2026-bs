package com.aigccopyright.controller;

import com.aigccopyright.common.Result;
import com.aigccopyright.entity.LicenseRecord;
import com.aigccopyright.service.AuthService;
import com.aigccopyright.service.LicenseRecordService;
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
@RequestMapping("/api/license")
public class LicenseController {

    @Autowired
    private LicenseRecordService licenseRecordService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<LicenseRecord>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            Long registerId,
                                            Integer status,
                                            String keyword) {
        return Result.success(licenseRecordService.page(pageNum, pageSize, registerId, status, keyword));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody LicenseRecord entity) {
        authService.assertCreator(role);
        licenseRecordService.saveEntity(entity);
        operationLogService.record(userId, "授权使用", "新增", "新增授权：" + entity.getLicensee());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody LicenseRecord entity) {
        authService.assertCreator(role);
        licenseRecordService.saveEntity(entity);
        operationLogService.record(userId, "授权使用", "编辑", "编辑授权：" + entity.getLicensee());
        return Result.success();
    }

    @PutMapping("/revoke/{id}")
    public Result<Void> revoke(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertCreator(role);
        licenseRecordService.revoke(id);
        operationLogService.record(userId, "授权使用", "撤销", "撤销授权：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertCreator(role);
        licenseRecordService.removeById(id);
        operationLogService.record(userId, "授权使用", "删除", "删除授权：" + id);
        return Result.success();
    }
}
