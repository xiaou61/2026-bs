package com.adoption.controller;

import com.adoption.common.Result;
import com.adoption.entity.AdopterProfile;
import com.adoption.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public Result<AdopterProfile> profile(@RequestAttribute("userId") Long userId) {
        return Result.success(userService.getProfile(userId));
    }

    @PutMapping("/profile/update")
    public Result<String> updateProfile(@RequestAttribute("userId") Long userId, @RequestBody AdopterProfile profile) {
        userService.updateProfile(userId, profile);
        return Result.success();
    }
}
