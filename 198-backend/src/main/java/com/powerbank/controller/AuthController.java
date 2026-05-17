package com.powerbank.controller;

import com.powerbank.common.Result;
import com.powerbank.dto.LoginRequest;
import com.powerbank.entity.SysUser;
import com.powerbank.clerk.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }

    @GetMapping("/info")
    public Result<SysUser> info(@RequestHeader("Authorization") String token) {
        return Result.success(authService.info(token));
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader(value = "Authorization", required = false) String token) {
        authService.logout(token);
        return Result.success();
    }
}
