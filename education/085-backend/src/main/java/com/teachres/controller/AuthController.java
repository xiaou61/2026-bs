package com.teachres.controller;

import com.teachres.common.Result;
import com.teachres.entity.SysUser;
import com.teachres.service.AuthService;
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
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        return Result.success(authService.login(params.get("username"), params.get("password")));
    }

    @GetMapping("/info")
    public Result<SysUser> info(@RequestAttribute("userId") Long userId) {
        return Result.success(authService.info(userId));
    }

    @PostMapping("/logout")
    public Result<String> logout(@RequestAttribute("userId") Long userId) {
        authService.logout(userId);
        return Result.success();
    }
}
