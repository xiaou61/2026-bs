package com.fraud.controller;

import com.fraud.common.Result;
import com.fraud.entity.User;
import com.fraud.service.OperationLogService;
import com.fraud.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    private UserService userService;

    @Resource
    private OperationLogService operationLogService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params) {
        Map<String, Object> data = userService.login(params.get("username"), params.get("password"));
        Object userObj = data.get("user");
        if (userObj instanceof User) {
            User loginUser = (User) userObj;
            operationLogService.add("USER", "LOGIN", loginUser.getId(), loginUser.getRole(), loginUser.getUsername(), "用户登录");
        } else {
            operationLogService.add("USER", "LOGIN", null, null, params.get("username"), "用户登录");
        }
        return Result.success(data);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        User created = userService.register(user);
        operationLogService.add("USER", "REGISTER", created.getId(), created.getRole(), created.getUsername(), "用户注册");
        return Result.success(created);
    }

    @GetMapping("/info")
    public Result<?> info(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(userService.getById(userId));
    }

    @PutMapping("/password")
    public Result<?> updatePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updatePassword(userId, params.get("oldPassword"), params.get("newPassword"));
        operationLogService.add("USER", "PASSWORD", userId, (String) request.getAttribute("role"), String.valueOf(userId), "修改密码");
        return Result.success();
    }

    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.logout(userId);
        operationLogService.add("USER", "LOGOUT", userId, (String) request.getAttribute("role"), String.valueOf(userId), "用户登出");
        return Result.success();
    }
}
