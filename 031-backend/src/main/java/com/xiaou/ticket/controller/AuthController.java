package com.xiaou.ticket.controller;

import com.xiaou.ticket.dto.Result;
import com.xiaou.ticket.entity.User;
import com.xiaou.ticket.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            Map<String, Object> result = authService.login(username, password);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        try {
            User registered = authService.register(user);
            return Result.success(registered);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
