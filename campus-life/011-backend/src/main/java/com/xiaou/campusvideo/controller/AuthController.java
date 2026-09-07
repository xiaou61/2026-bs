package com.xiaou.campusvideo.controller;

import com.xiaou.campusvideo.entity.User;
import com.xiaou.campusvideo.service.UserService;
import com.xiaou.campusvideo.util.JwtUtil;
import com.xiaou.campusvideo.util.Result;
import com.xiaou.campusvideo.util.UserHolder;
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
    
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        try {
            userService.register(user);
            return Result.success("注册成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        User loginUser = userService.login(user.getUsername(), user.getPassword());
        if (loginUser == null) {
            return Result.error("用户名或密码错误");
        }
        
        if (loginUser.getStatus() == 0) {
            return Result.error("账号已被禁用");
        }
        
        String token = jwtUtil.generateToken(loginUser.getId(), loginUser.getUsername());
        
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userInfo", loginUser);
        
        return Result.success(data);
    }
    
    @PostMapping("/logout")
    public Result<?> logout() {
        return Result.success("登出成功");
    }
    
    @GetMapping("/info")
    public Result<User> getInfo() {
        Long userId = UserHolder.getUserId();
        if (userId == null) {
            return Result.error("未登录");
        }
        
        User user = userService.getById(userId);
        return Result.success(user);
    }
}

