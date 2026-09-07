package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.CreditLog;
import com.xiaou.entity.User;
import com.xiaou.entity.Violation;
import com.xiaou.service.CreditLogService;
import com.xiaou.service.UserService;
import com.xiaou.service.ViolationService;
import com.xiaou.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CreditLogService creditLogService;
    
    @Autowired
    private ViolationService violationService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        User registered = userService.register(user);
        return Result.success(registered);
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String studentNo = params.get("studentNo");
        String password = params.get("password");
        User user = userService.login(studentNo, password);
        String token = jwtUtil.generateToken(user.getId(), user.getRole());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return Result.success(result);
    }

    @GetMapping("/info")
    public Result<User> getInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getById(userId);
        return Result.success(user);
    }

    @PutMapping("/password")
    public Result<String> updatePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        userService.updatePassword(userId, oldPassword, newPassword);
        return Result.success("修改成功");
    }

    @GetMapping("/credit-log")
    public Result<List<CreditLog>> getCreditLog(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<CreditLog> logs = creditLogService.listMyCreditLogs(userId);
        return Result.success(logs);
    }

    @GetMapping("/violation")
    public Result<List<Violation>> getViolations(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Violation> violations = violationService.listMyViolations(userId);
        return Result.success(violations);
    }

    @PostMapping("/violation/appeal")
    public Result<String> appealViolation(@RequestBody Map<String, Object> params) {
        Long violationId = Long.valueOf(params.get("violationId").toString());
        String reason = params.get("reason").toString();
        String images = params.get("images") != null ? params.get("images").toString() : null;
        violationService.appeal(violationId, reason, images);
        return Result.success("申诉已提交");
    }
}

