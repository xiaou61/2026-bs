package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        return Result.success(authService.login(username, password));
    }

    @PostMapping("/wxLogin")
    public Result<?> wxLogin(@RequestBody Map<String, String> params) {
        String openid = params.get("openid");
        String nickname = params.get("nickname");
        String avatar = params.get("avatar");
        return Result.success(authService.wxLogin(openid, nickname, avatar));
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String nickname = params.get("nickname");
        authService.register(username, password, nickname);
        return Result.success();
    }
}
