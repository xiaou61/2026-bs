package com.classic.controller;

import com.classic.common.Result;
import com.classic.entity.User;
import com.classic.service.UserService;
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

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params) {
        return Result.success(userService.login(params.get("username"), params.get("password")));
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        return Result.success(userService.register(user));
    }

    @GetMapping("/info")
    public Result<?> info(HttpServletRequest request) {
        return Result.success(userService.getById((Long) request.getAttribute("userId")));
    }

    @PutMapping("/password")
    public Result<?> updatePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        userService.updatePassword((Long) request.getAttribute("userId"), params.get("oldPassword"), params.get("newPassword"));
        return Result.success();
    }

    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request) {
        userService.logout((Long) request.getAttribute("userId"));
        return Result.success();
    }
}
