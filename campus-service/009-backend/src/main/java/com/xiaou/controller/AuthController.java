package com.xiaou.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.User;
import com.xiaou.service.UserService;
import com.xiaou.utils.JwtUtil;
import com.xiaou.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("用户名或密码不能为空");
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        if (userService.count(wrapper) > 0) {
            return Result.error("用户名已存在");
        }

        user.setPassword(MD5Util.encrypt(user.getPassword()));
        if (StrUtil.isBlank(user.getRole())) {
            user.setRole("STUDENT");
        }
        user.setStatus(1);
        userService.save(user);
        return Result.success("注册成功");
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("用户名或密码不能为空");
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        User dbUser = userService.getOne(wrapper);

        if (dbUser == null) {
            return Result.error("用户名或密码错误");
        }

        if (!dbUser.getPassword().equals(MD5Util.encrypt(user.getPassword()))) {
            return Result.error("用户名或密码错误");
        }

        if (dbUser.getStatus() == 0) {
            return Result.error("账号已被禁用");
        }

        String token = JwtUtil.generateToken(dbUser.getId(), dbUser.getUsername(), dbUser.getRole());

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        dbUser.setPassword(null);
        data.put("userInfo", dbUser);

        return Result.success("登录成功", data);
    }

    @GetMapping("/info")
    public Result<?> info(@RequestHeader("Authorization") String token) {
        try {
            Long userId = JwtUtil.getUserId(token);
            User user = userService.getById(userId);
            if (user == null) {
                return Result.error("用户不存在");
            }
            user.setPassword(null);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("token无效");
        }
    }
}

