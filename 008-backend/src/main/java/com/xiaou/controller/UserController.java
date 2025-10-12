package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.dto.LoginDTO;
import com.xiaou.dto.RegisterDTO;
import com.xiaou.dto.UserUpdateDTO;
import com.xiaou.entity.User;
import com.xiaou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterDTO dto) {
        userService.register(dto);
        return Result.success("注册成功");
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginDTO dto) {
        Map<String, Object> result = userService.login(dto);
        return Result.success(result);
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestAttribute("userId") Long userId) {
        User user = userService.getUserInfo(userId);
        return Result.success(user);
    }

    @PutMapping("/info")
    public Result<String> updateUserInfo(@RequestAttribute("userId") Long userId, 
                                         @RequestBody UserUpdateDTO dto) {
        userService.updateUserInfo(userId, dto);
        return Result.success("更新成功");
    }
}

