package com.xiaou.resource.controller;

import com.xiaou.resource.common.Result;
import com.xiaou.resource.entity.User;
import com.xiaou.resource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<Object> register(@RequestBody User user) {
        Map<String, Object> result = userService.register(user);
        if ((Boolean) result.get("success")) {
            return Result.success(result.get("message"));
        }
        return Result.error((String) result.get("message"));
    }

    @PostMapping("/login")
    public Result<Object> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        Map<String, Object> result = userService.login(username, password);
        if ((Boolean) result.get("success")) {
            return Result.success(result);
        }
        return Result.error((String) result.get("message"));
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestAttribute Long userId) {
        User user = userService.getUserInfo(userId);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result<String> updateUserInfo(@RequestAttribute Long userId, @RequestBody User user) {
        user.setId(userId);
        boolean success = userService.updateUserInfo(user);
        return success ? Result.success("更新成功") : Result.error("更新失败");
    }

    @GetMapping("/points")
    public Result<Integer> getPoints(@RequestAttribute Long userId) {
        User user = userService.getUserInfo(userId);
        return Result.success(user.getPoints());
    }
}

