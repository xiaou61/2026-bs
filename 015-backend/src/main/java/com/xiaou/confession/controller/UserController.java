package com.xiaou.confession.controller;

import com.xiaou.confession.common.Result;
import com.xiaou.confession.entity.User;
import com.xiaou.confession.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            User user = userService.getUserById(userId);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PostMapping("/auth/submit")
    public Result<Void> submitAuth(@RequestBody Map<String, String> params, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            String authImage = params.get("authImage");
            
            userService.submitAuth(userId, authImage);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            String school = (String) params.get("school");
            String college = (String) params.get("college");
            Integer grade = (Integer) params.get("grade");
            
            userService.updateProfile(userId, school, college, grade);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            String oldPassword = params.get("oldPassword");
            String newPassword = params.get("newPassword");
            
            userService.updatePassword(userId, oldPassword, newPassword);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

