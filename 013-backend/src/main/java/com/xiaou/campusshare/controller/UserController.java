package com.xiaou.campusshare.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.entity.CreditLog;
import com.xiaou.campusshare.entity.User;
import com.xiaou.campusshare.entity.UserAuth;
import com.xiaou.campusshare.service.CreditService;
import com.xiaou.campusshare.service.UserService;
import com.xiaou.campusshare.mapper.UserAuthMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private CreditService creditService;

    @GetMapping("/profile")
    public Result<User> getProfile(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getUserById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }

    @PutMapping("/profile")
    public Result<?> updateProfile(HttpServletRequest request, @RequestBody User user) {
        Long userId = (Long) request.getAttribute("userId");
        user.setId(userId);
        user.setPassword(null);
        user.setUsername(null);
        user.setPhone(null);
        userService.updateById(user);
        return Result.success("更新成功");
    }

    @PostMapping("/auth/submit")
    public Result<?> submitAuth(HttpServletRequest request, @RequestBody UserAuth userAuth) {
        Long userId = (Long) request.getAttribute("userId");
        userAuth.setUserId(userId);
        userAuth.setAuthStatus(0);
        userAuthMapper.insert(userAuth);
        
        User user = userService.getUserById(userId);
        user.setAuthStatus(1);
        userService.updateById(user);
        
        return Result.success("提交成功，等待审核");
    }

    @GetMapping("/auth/status")
    public Result<UserAuth> getAuthStatus(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        LambdaQueryWrapper<UserAuth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAuth::getUserId, userId);
        wrapper.orderByDesc(UserAuth::getCreateTime);
        wrapper.last("LIMIT 1");
        UserAuth userAuth = userAuthMapper.selectOne(wrapper);
        return Result.success(userAuth);
    }

    @GetMapping("/credit")
    public Result<User> getCredit(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getUserById(userId);
        return Result.success(user);
    }

    @GetMapping("/credit/log")
    public Result<List<CreditLog>> getCreditLog(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        LambdaQueryWrapper<CreditLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CreditLog::getUserId, userId);
        wrapper.orderByDesc(CreditLog::getCreateTime);
        List<CreditLog> logs = creditService.list(wrapper);
        return Result.success(logs);
    }
}

