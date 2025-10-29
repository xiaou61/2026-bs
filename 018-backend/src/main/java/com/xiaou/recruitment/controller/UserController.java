package com.xiaou.recruitment.controller;

import com.xiaou.recruitment.common.Result;
import com.xiaou.recruitment.entity.User;
import com.xiaou.recruitment.service.UserService;
import com.xiaou.recruitment.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        try {
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("student");
            }
            user.setStatus(1);
            userService.register(user);
            user.setPassword(null);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("注册失败：" + e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        User user = userService.login(username, password);
        if (user != null) {
            String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            user.setPassword(null);
            data.put("user", user);
            return Result.success(data);
        }
        return Result.error("用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<?> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getUserById(userId);
        if (user != null) {
            user.setPassword(null);
            return Result.success(user);
        }
        return Result.error("用户不存在");
    }

    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody User user, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        user.setId(userId);
        if (userService.updateUser(user)) {
            user.setPassword(null);
            return Result.success(user);
        }
        return Result.error("更新失败");
    }
}
