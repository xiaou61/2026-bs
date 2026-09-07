package com.promptops.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.promptops.common.Result;
import com.promptops.entity.SysUser;
import com.promptops.service.AuthService;
import com.promptops.service.OperationLogService;
import com.promptops.service.UserService;
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
    private UserService userService;

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
        return Result.success(userService.page(pageNum, pageSize, keyword, userRole, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody SysUser user) {
        authService.assertAdmin(role);
        userService.saveUser(user);
        operationLogService.record(userId, "用户管理", "新增", "新增用户：" + user.getUsername());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody SysUser user) {
        authService.assertAdmin(role);
        userService.saveUser(user);
        operationLogService.record(userId, "用户管理", "编辑", "编辑用户：" + user.getUsername());
        return Result.success();
    }

    @PutMapping("/status/{id}/{status}")
    public Result<Void> status(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id, @PathVariable Integer status) {
        authService.assertAdmin(role);
        SysUser user = new SysUser();
        user.setId(id);
        user.setStatus(status);
        userService.updateById(user);
        operationLogService.record(userId, "用户管理", "状态", "调整用户状态：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        userService.removeById(id);
        operationLogService.record(userId, "用户管理", "删除", "删除用户：" + id);
        return Result.success();
    }
}
