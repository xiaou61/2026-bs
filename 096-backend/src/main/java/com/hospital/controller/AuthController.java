package com.hospital.controller;

import com.hospital.common.Result;
import com.hospital.entity.SysUser;
import com.hospital.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        return Result.success(authService.login(params.get("username"), params.get("password")));
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody Map<String, String> params) {
        authService.register(params);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<SysUser> info(@RequestAttribute("userId") Long userId) {
        return Result.success(authService.info(userId));
    }

    @PutMapping("/password")
    public Result<String> updatePassword(@RequestAttribute("userId") Long userId, @RequestBody Map<String, String> params) {
        authService.updatePassword(userId, params.get("oldPassword"), params.get("newPassword"));
        return Result.success();
    }

    @PostMapping("/logout")
    public Result<String> logout(@RequestAttribute("userId") Long userId) {
        authService.logout(userId);
        return Result.success();
    }
}
