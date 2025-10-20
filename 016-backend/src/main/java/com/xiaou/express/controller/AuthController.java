package com.xiaou.express.controller;

import com.xiaou.express.common.Result;
import com.xiaou.express.dto.LoginRequest;
import com.xiaou.express.dto.RegisterRequest;
import com.xiaou.express.entity.User;
import com.xiaou.express.service.AuthService;
import com.xiaou.express.service.UserService;
import com.xiaou.express.util.UserContext;
import com.xiaou.express.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<Void> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginRequest request) {
        LoginVO loginVO = authService.login(request);
        return Result.success(loginVO);
    }

    @GetMapping("/info")
    public Result<User> getUserInfo() {
        Long userId = UserContext.getCurrentUserId();
        User user = userService.getUserById(userId);
        user.setPassword(null);
        return Result.success(user);
    }
}

