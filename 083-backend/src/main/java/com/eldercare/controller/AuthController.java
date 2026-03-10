package com.eldercare.controller;

import com.eldercare.common.Result;
import com.eldercare.dto.LoginDTO;
import com.eldercare.entity.SysUser;
import com.eldercare.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
        return Result.success(authService.login(loginDTO));
    }

    @GetMapping("/info")
    public Result<SysUser> info(@RequestAttribute("userId") String userId) {
        return Result.success(authService.info(Long.valueOf(userId)));
    }

    @PostMapping("/logout")
    public Result<String> logout(@RequestAttribute("userId") String userId) {
        authService.logout(Long.valueOf(userId));
        return Result.success();
    }
}
