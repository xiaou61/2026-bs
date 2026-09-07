package com.xiaou.controller;

import cn.hutool.crypto.digest.DigestUtil;
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

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        User existUser = userService.getUserByUsername(user.getUsername());
        if (existUser != null) {
            return Result.error("用户名已存在");
        }

        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        userService.register(user);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        User dbUser = userService.getUserByUsername(user.getUsername());
        if (dbUser == null) {
            return Result.error("用户不存在");
        }

        if (!DigestUtil.md5Hex(user.getPassword()).equals(dbUser.getPassword())) {
            return Result.error("密码错误");
        }

        if (dbUser.getStatus() == 0) {
            return Result.error("账号已被禁用");
        }

        String token = jwtUtil.generateToken(dbUser.getId(), dbUser.getUsername(), dbUser.getRole());
        
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", dbUser);
        dbUser.setPassword(null);

        return Result.success(data);
    }

    @GetMapping("/info")
    public Result<?> info(@RequestAttribute Long userId) {
        User user = userService.getById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }

    @PostMapping("/logout")
    public Result<?> logout() {
        return Result.success();
    }
}

