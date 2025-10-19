package com.xiaou.confession.controller;

import com.xiaou.confession.common.Result;
import com.xiaou.confession.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/register")
    public Result<Map<String, Object>> register(@RequestBody Map<String, Object> params) {
        try {
            String studentId = (String) params.get("studentId");
            String realName = (String) params.get("realName");
            String phone = (String) params.get("phone");
            String password = (String) params.get("password");
            String school = (String) params.get("school");
            String college = (String) params.get("college");
            Integer grade = (Integer) params.get("grade");
            
            Map<String, Object> result = authService.userRegister(studentId, realName, phone, password, school, college, grade);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        try {
            String account = params.get("account");
            String password = params.get("password");
            
            Map<String, Object> result = authService.userLogin(account, password);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PostMapping("/admin/login")
    public Result<Map<String, Object>> adminLogin(@RequestBody Map<String, String> params) {
        try {
            String username = params.get("username");
            String password = params.get("password");
            
            Map<String, Object> result = authService.adminLogin(username, password);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

