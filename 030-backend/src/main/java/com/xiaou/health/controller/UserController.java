package com.xiaou.health.controller;

import com.xiaou.health.common.Result;
import com.xiaou.health.dto.LoginRequest;
import com.xiaou.health.dto.LoginResponse;
import com.xiaou.health.dto.RegisterRequest;
import com.xiaou.health.entity.User;
import com.xiaou.health.service.UserService;
import com.xiaou.health.util.UserContext;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result<User> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.login(request);
            return Result.success(response);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/info")
    public Result<User> getUserInfo() {
        try {
            Long userId = UserContext.getUserId();
            User user = userService.getUserInfo(userId);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/info")
    public Result<User> updateUserInfo(@RequestBody User request) {
        try {
            Long userId = UserContext.getUserId();
            User user = userService.updateUserInfo(userId, request);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
