package com.xiaou.campusclub.controller;

import com.xiaou.campusclub.common.Result;
import com.xiaou.campusclub.dto.LoginRequest;
import com.xiaou.campusclub.dto.RegisterRequest;
import com.xiaou.campusclub.entity.User;
import com.xiaou.campusclub.service.UserService;
import com.xiaou.campusclub.vo.LoginVO;
import com.xiaou.campusclub.vo.UserInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public Result<LoginVO> register(@RequestBody RegisterRequest request) {
        return Result.success(userService.register(request));
    }
    
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginRequest request) {
        return Result.success(userService.login(request));
    }
    
    @GetMapping("/info")
    public Result<UserInfoVO> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(userService.getUserInfo(userId));
    }
    
    @PutMapping("/profile")
    public Result<Void> updateProfile(HttpServletRequest request, @RequestBody User user) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updateProfile(userId, user);
        return Result.success();
    }
    
    @PostMapping("/interests")
    public Result<Void> addInterest(HttpServletRequest request, @RequestParam Long interestId) {
        Long userId = (Long) request.getAttribute("userId");
        userService.addUserInterest(userId, interestId);
        return Result.success();
    }
    
    @DeleteMapping("/interests/{interestId}")
    public Result<Void> removeInterest(HttpServletRequest request, @PathVariable Long interestId) {
        Long userId = (Long) request.getAttribute("userId");
        userService.removeUserInterest(userId, interestId);
        return Result.success();
    }
}

