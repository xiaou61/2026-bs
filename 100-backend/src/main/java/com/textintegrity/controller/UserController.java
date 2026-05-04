package com.textintegrity.controller;

import com.github.pagehelper.PageInfo;
import com.textintegrity.common.Result;
import com.textintegrity.service.AccessLogService;
import com.textintegrity.service.AuthService;
import com.textintegrity.service.CrudService;
import com.textintegrity.utils.Filters;
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
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private CrudService crudService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AccessLogService accessLogService;

    @GetMapping("/page")
    public Result<PageInfo<Map<String, Object>>> page(@RequestAttribute String role,
                                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      String keyword,
                                                      @RequestParam(name = "role", required = false) String userRole,
                                                      Integer status) {
        authService.assertAdmin(role);
        return Result.success(crudService.page("user", pageNum, pageSize, keyword, Filters.of("role", userRole, "status", status)));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertAdmin(role);
        data.putIfAbsent("password", "123456");
        crudService.save("user", data);
        accessLogService.record(userId, "用户管理", "新增", "新增用户：" + data.get("username"));
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertAdmin(role);
        crudService.save("user", data);
        accessLogService.record(userId, "用户管理", "编辑", "编辑用户：" + data.get("username"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        crudService.delete("user", id);
        accessLogService.record(userId, "用户管理", "删除", "删除用户：" + id);
        return Result.success();
    }
}

