package com.xiaou.studyroom.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.studyroom.common.Result;
import com.xiaou.studyroom.entity.User;
import com.xiaou.studyroom.service.UserService;
import com.xiaou.studyroom.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginMap) {
        String username = loginMap.get("username");
        String password = loginMap.get("password");

        User user = userService.login(username, password);
        if (user == null) {
            return Result.error("用户名或密码错误");
        }

        Map<String, Object> tokenMap = new HashMap<>();
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        tokenMap.put("token", token);
        tokenMap.put("user", user);

        return Result.success("登录成功", tokenMap);
    }

    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody User user) {
        if (userService.register(user)) {
            return Result.success("注册成功");
        } else {
            return Result.error("注册失败，用户名已存在");
        }
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            String actualToken = token.replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(actualToken);
            User user = userService.getById(userId);
            if (user != null) {
                user.setPassword(null);
                return Result.success(user);
            }
            return Result.error("用户不存在");
        } catch (Exception e) {
            return Result.error("获取用户信息失败");
        }
    }

    @PutMapping("/update")
    public Result<String> updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
        try {
            String actualToken = token.replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(actualToken);
            user.setId(userId);
            if (userService.updateById(user)) {
                return Result.success("更新成功");
            }
            return Result.error("更新失败");
        } catch (Exception e) {
            return Result.error("更新用户信息失败");
        }
    }

    @GetMapping("/page")
    public Result<Page<User>> getUserPage(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        Page<User> page = userService.getUserPage(current, size, keyword);
        page.getRecords().forEach(user -> user.setPassword(null));
        return Result.success(page);
    }
}