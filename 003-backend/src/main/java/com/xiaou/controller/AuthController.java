package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.User;
import com.xiaou.service.UserService;
import com.xiaou.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        User user = userService.login(username, password);
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);

        return Result.success(data);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        userService.register(user);
        return Result.success("注册成功");
    }

    @PostMapping("/change-password")
    public Result<?> changePassword(@RequestBody Map<String, String> params, @RequestAttribute Long userId) {
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        userService.changePassword(userId, oldPassword, newPassword);
        return Result.success("密码修改成功");
    }

    @GetMapping("/current")
    public Result<?> getCurrentUser(@RequestAttribute Long userId) {
        User user = userService.getById(userId);
        user.setPassword(null);
        return Result.success(user);
    }
}

