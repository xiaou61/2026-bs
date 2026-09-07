package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.entity.SysUser;
import com.legalcase.service.AuthService;
import com.legalcase.service.OperationLogService;
import com.legalcase.service.SysUserService;
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
@RequestMapping("/api/user")
public class SysUserController {
    @Autowired
    private SysUserService service;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<PageInfo<SysUser>> page(@RequestAttribute String role,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          String keyword,
                                          @RequestParam(name = "role", required = false) String queryRole,
                                          Integer status) {
        authService.assertAdmin(role);
        return Result.success(service.page(pageNum, pageSize, keyword, queryRole, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody SysUser sysUser) {
        authService.assertAdmin(role);
        service.saveEntity(sysUser);
        operationLogService.record(userId, "账号权限", "新增", "新增账号权限");
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody SysUser sysUser) {
        authService.assertAdmin(role);
        service.saveEntity(sysUser);
        operationLogService.record(userId, "账号权限", "编辑", "编辑账号权限：" + sysUser.getId());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        operationLogService.record(userId, "账号权限", "删除", "删除账号权限：" + id);
        return Result.success();
    }
}
