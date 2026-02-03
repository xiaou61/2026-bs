package com.enrollment.controller;

import com.enrollment.common.Result;
import com.enrollment.entity.Admin;
import com.enrollment.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        return Result.success(authService.login(username, password));
    }

    @GetMapping("/info")
    public Result<?> getInfo(HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        Admin admin = authService.getAdminById(userId);
        return Result.success(admin);
    }

    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        authService.logout(userId);
        return Result.success();
    }

    @PutMapping("/password")
    public Result<?> updatePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        authService.updatePassword(userId, oldPassword, newPassword);
        return Result.success();
    }
}
