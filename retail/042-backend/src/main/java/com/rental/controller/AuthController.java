package com.rental.controller;

import com.rental.common.Result;
import com.rental.entity.User;
import com.rental.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterDTO dto) {
        authService.register(dto.getUsername(), dto.getPassword(), dto.getRole(), dto.getPhone());
        return Result.success("注册成功");
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginDTO dto) {
        Map<String, Object> result = authService.login(dto.getUsername(), dto.getPassword());
        return Result.success(result);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/userinfo")
    public Result<User> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = authService.getUserInfo(userId);
        return Result.success(user);
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Result<?> changePassword(HttpServletRequest request, @RequestBody ChangePasswordDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        authService.changePassword(userId, dto.getOldPassword(), dto.getNewPassword());
        return Result.success("密码修改成功");
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/userinfo")
    public Result<?> updateUserInfo(HttpServletRequest request, @RequestBody User user) {
        Long userId = (Long) request.getAttribute("userId");
        authService.updateUserInfo(userId, user);
        return Result.success("更新成功");
    }

    @Data
    public static class RegisterDTO {
        private String username;
        private String password;
        private String role;
        private String phone;
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }

    @Data
    public static class ChangePasswordDTO {
        private String oldPassword;
        private String newPassword;
    }
}
