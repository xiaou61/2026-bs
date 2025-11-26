package com.xiaou.pet.controller;

import com.xiaou.pet.common.Result;
import com.xiaou.pet.entity.User;
import com.xiaou.pet.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/{id}")
    public Result<User> getUserInfo(@PathVariable Long id) {
        return userService.getUserInfo(id);
    }

    @PutMapping("/update")
    public Result<User> updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }
}
