package com.xiaou.campusshare.controller;

import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.entity.User;
import com.xiaou.campusshare.service.AuthService;
import com.xiaou.campusshare.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<?> register(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String nickname = params.get("nickname");
        String phone = params.get("phone");
        String school = params.get("school");

        Map<String, Object> result = authService.register(username, password, nickname, phone, school);
        
        if ((Boolean) result.get("success")) {
            return Result.success(result.get("message").toString());
        } else {
            return Result.error(result.get("message").toString());
        }
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        Map<String, Object> result = authService.login(username, password);
        
        if ((Boolean) result.get("success")) {
            return Result.success(result);
        } else {
            return Result.error(result.get("message").toString());
        }
    }

    @GetMapping("/info")
    public Result<User> getInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getUserById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }

    @PostMapping("/logout")
    public Result<?> logout() {
        return Result.success("登出成功");
    }
}

