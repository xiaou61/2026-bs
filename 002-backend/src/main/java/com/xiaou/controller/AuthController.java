package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.User;
import com.xiaou.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Data
    public static class LoginRequest {
        @NotBlank(message = "用户名不能为空")
        private String username;
        @NotBlank(message = "密码不能为空")
        private String password;
    }

    @Data
    public static class RegisterRequest {
        @NotBlank(message = "用户名不能为空")
        private String username;
        @NotBlank(message = "密码不能为空")
        private String password;
        @NotBlank(message = "姓名不能为空")
        private String name;
        private String email;
        private String phone;
        private String studentNo;
        private String major;
        private String className;
    }

    @Data
    public static class ChangePasswordRequest {
        @NotBlank(message = "原密码不能为空")
        private String oldPassword;
        @NotBlank(message = "新密码不能为空")
        private String newPassword;
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        Map<String, Object> result = authService.login(request.getUsername(), request.getPassword());
        return Result.success(result);
    }

    @PostMapping("/register")
    public Result<User> register(@Valid @RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStudentNo(request.getStudentNo());
        user.setMajor(request.getMajor());
        user.setClassName(request.getClassName());
        user.setRole("student");
        User newUser = authService.register(user);
        return Result.success(newUser);
    }

    @GetMapping("/info")
    public Result<Map<String, Object>> getInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String username = (String) request.getAttribute("username");
        String role = (String) request.getAttribute("role");
        
        Map<String, Object> info = Map.of(
            "userId", userId,
            "username", username,
            "role", role
        );
        return Result.success(info);
    }

    @PostMapping("/change-password")
    public Result<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request, HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        authService.changePassword(userId, request.getOldPassword(), request.getNewPassword());
        return Result.success();
    }
}

