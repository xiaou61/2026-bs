package com.diet.management.controller;

import com.diet.management.common.Result;
import com.diet.management.entity.User;
import com.diet.management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        userService.register(user);
        return Result.success("注册成功");
    }
    
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<String> login(@RequestParam String username, @RequestParam String password) {
        String token = userService.login(username, password);
        return Result.success(token);
    }
    
    @Operation(summary = "获取用户信息")
    @GetMapping("/{id}")
    public Result<User> getUserInfo(@PathVariable Long id) {
        User user = userService.getById(id);
        user.setPassword(null); // 隐藏密码
        return Result.success(user);
    }
    
    @Operation(summary = "更新用户信息")
    @PutMapping
    public Result<String> updateUser(@RequestBody User user) {
        userService.updateUserInfo(user);
        return Result.success("更新成功");
    }
}
