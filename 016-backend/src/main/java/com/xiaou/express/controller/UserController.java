package com.xiaou.express.controller;

import com.xiaou.express.common.Result;
import com.xiaou.express.dto.PasswordUpdateRequest;
import com.xiaou.express.dto.UserProfileUpdateRequest;
import com.xiaou.express.entity.User;
import com.xiaou.express.service.UserService;
import com.xiaou.express.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public Result<User> getProfile() {
        Long userId = UserContext.getCurrentUserId();
        return Result.success(userService.getUserById(userId));
    }

    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody UserProfileUpdateRequest request) {
        Long userId = UserContext.getCurrentUserId();
        userService.updateProfile(userId, request);
        return Result.success();
    }

    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestBody PasswordUpdateRequest request) {
        Long userId = UserContext.getCurrentUserId();
        userService.updatePassword(userId, request);
        return Result.success();
    }
}
