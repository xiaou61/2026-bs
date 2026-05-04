package com.legalcase.controller;

import com.legalcase.common.Result;
import com.legalcase.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> form) {
        return Result.success(authService.login(form.get("username"), form.get("password")));
    }

    @GetMapping("/info")
    public Result<Object> info(@RequestAttribute Long userId) {
        return Result.success(authService.info(userId));
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestAttribute Long userId) {
        authService.logout(userId);
        return Result.success();
    }
}
