package com.xiaou.ailearning.controller;

import com.xiaou.ailearning.common.Result;
import com.xiaou.ailearning.entity.User;
import com.xiaou.ailearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户API控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            
            String token = userService.login(username, password);
            User user = userService.findByUsername(username);
            
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", user);
            
            return Result.success("登录成功", data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody User user) {
        try {
            userService.register(user);
            return Result.success("注册成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestAttribute Long userId) {
        User user = userService.getById(userId);
        if (user != null) {
            user.setPassword(null); // 不返回密码
            return Result.success(user);
        }
        return Result.error("用户不存在");
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/info")
    public Result<Void> updateUserInfo(@RequestAttribute Long userId, @RequestBody User user) {
        try {
            user.setId(userId);
            user.setPassword(null); // 不允许通过此接口修改密码
            userService.updateById(user);
            return Result.success("更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新学习偏好
     */
    @PutMapping("/preferences")
    public Result<Void> updatePreferences(@RequestAttribute Long userId, @RequestBody User preferences) {
        try {
            userService.updateLearningPreferences(userId, preferences);
            return Result.success("偏好设置更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}