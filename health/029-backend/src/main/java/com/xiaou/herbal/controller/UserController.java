package com.xiaou.herbal.controller;

import com.xiaou.herbal.common.Result;
import com.xiaou.herbal.dto.LoginRequest;
import com.xiaou.herbal.dto.LoginResponse;
import com.xiaou.herbal.dto.RegisterRequest;
import com.xiaou.herbal.entity.User;
import com.xiaou.herbal.service.UserService;
import com.xiaou.herbal.util.UserContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result<Void> register(@Validated @RequestBody RegisterRequest request) {
        try {
            userService.register(request);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@Validated @RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.login(request);
            return Result.success(response);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/info")
    public Result<User> getUserInfo() {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        User user = userService.getUserInfo(userId);
        return Result.success(user);
    }

    @PutMapping("/info")
    public Result<Void> updateUserInfo(@RequestBody User user) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            userService.updateUserInfo(userId, user);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
