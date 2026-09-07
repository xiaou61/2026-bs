package com.xiaou.sport.controller;

import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.User;
import com.xiaou.sport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestAttribute Long userId) {
        User user = userService.getById(userId);
        user.setPassword(null);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result<Void> updateUser(@RequestAttribute Long userId, @RequestBody User user) {
        user.setId(userId);
        user.setPassword(null);
        user.setUsername(null);
        userService.updateById(user);
        return Result.success();
    }
}
