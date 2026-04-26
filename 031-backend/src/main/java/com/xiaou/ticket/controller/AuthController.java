package com.xiaou.ticket.controller;

import com.xiaou.ticket.dto.LoginRequest;
import com.xiaou.ticket.dto.RegisterRequest;
import com.xiaou.ticket.dto.Result;
import com.xiaou.ticket.entity.User;
import com.xiaou.ticket.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Map<String, Object>> loginJson(@RequestBody LoginRequest request) {
        return doLogin(request);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Result<Map<String, Object>> loginForm(LoginRequest request) {
        return doLogin(request);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<User> registerJson(@RequestBody RegisterRequest request) {
        return doRegister(request);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Result<User> registerForm(RegisterRequest request) {
        return doRegister(request);
    }

    private Result<Map<String, Object>> doLogin(LoginRequest request) {
        try {
            return Result.success(authService.login(request.getUsername(), request.getPassword()));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    private Result<User> doRegister(RegisterRequest request) {
        try {
            return Result.success(authService.register(request));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
