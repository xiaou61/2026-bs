package com.servicequality.controller;

import com.servicequality.common.Result;
import com.servicequality.entity.SysUser;
import com.servicequality.service.AuthService;
import com.servicequality.service.OperationLogService;
import com.servicequality.service.SysUserService;
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
@RequestMapping("/api/user")
public class SysUserController {
    @Autowired
    private SysUserService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<SysUser>> page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, String keyword, String role, Integer status) {
        return Result.success(service.page(pageNum, pageSize, keyword, role, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody SysUser entity) {
        authService.assertAdmin(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "账号权限", "新增", "新增账号权限");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody SysUser entity) {
        authService.assertAdmin(role);
        service.saveEntity(entity);
        operationLogService.record(userId, "账号权限", "编辑", "编辑账号权限：" + entity.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.removeById(id);
        operationLogService.record(userId, "账号权限", "删除", "删除账号权限：" + id);
        return Result.success();
    }
}
