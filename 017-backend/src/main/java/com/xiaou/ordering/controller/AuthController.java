package com.xiaou.ordering.controller;

import com.xiaou.ordering.common.Result;
import com.xiaou.ordering.dto.LoginRequest;
import com.xiaou.ordering.dto.RegisterRequest;
import com.xiaou.ordering.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/user/register")
    public Result<Void> userRegister(@RequestBody RegisterRequest request) {
        authService.userRegister(request);
        return Result.success();
    }

    @PostMapping("/user/login")
    public Result<Map<String, String>> userLogin(@RequestBody LoginRequest request) {
        String token = authService.userLogin(request);
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return Result.success(data);
    }

    @PostMapping("/merchant/login")
    public Result<Map<String, String>> merchantLogin(@RequestBody LoginRequest request) {
        String token = authService.merchantLogin(request);
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return Result.success(data);
    }

    @PostMapping("/admin/login")
    public Result<Map<String, String>> adminLogin(@RequestBody LoginRequest request) {
        String token = authService.adminLogin(request);
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return Result.success(data);
    }
}

