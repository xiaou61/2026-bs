package com.recruitmatch.controller;

import com.recruitmatch.common.Result;
import com.recruitmatch.entity.SysUser;
import com.recruitmatch.service.AuthService;
import com.recruitmatch.service.OperationLogService;
import com.recruitmatch.service.SysUserService;
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
public class UserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<SysUser>> page(@RequestAttribute String role,
                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      String keyword,
                                      @RequestParam(name = "role", required = false) String userRole,
                                      Integer status) {
        authService.assertAdmin(role);
        return Result.success(sysUserService.page(pageNum, pageSize, keyword, userRole, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody SysUser user) {
        authService.assertAdmin(role);
        sysUserService.saveUser(user);
        operationLogService.record(userId, "用户管理", "新增", "新增用户：" + user.getUsername());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody SysUser user) {
        authService.assertAdmin(role);
        sysUserService.saveUser(user);
        operationLogService.record(userId, "用户管理", "编辑", "编辑用户：" + user.getUsername());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        sysUserService.removeById(id);
        operationLogService.record(userId, "用户管理", "删除", "删除用户：" + id);
        return Result.success();
    }
}
