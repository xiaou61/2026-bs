package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        return Result.success(userService.login(username, password));
    }

    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getAttribute("userId").toString());
        userService.logout(userId);
        return Result.success();
    }
}
