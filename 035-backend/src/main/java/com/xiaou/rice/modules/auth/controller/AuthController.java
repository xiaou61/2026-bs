package com.xiaou.rice.modules.auth.controller;

import com.xiaou.rice.common.api.Result;
import com.xiaou.rice.common.api.ResultCode;
import com.xiaou.rice.common.exception.BusinessException;
import com.xiaou.rice.modules.auth.dto.LoginRequest;
import com.xiaou.rice.modules.auth.dto.LoginResponse;
import com.xiaou.rice.modules.auth.dto.RegisterRequest;
import com.xiaou.rice.modules.user.entity.User;
import com.xiaou.rice.modules.user.mapper.UserMapper;
import com.xiaou.rice.modules.user.service.UserService;
import com.xiaou.rice.security.JwtUtil;
import com.xiaou.rice.security.SecurityUtil;
import com.xiaou.rice.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public Result<LoginResponse> register(@Valid @RequestBody RegisterRequest req) {
        User exist = userMapper.findByUsername(req.getUsername());
        if (exist != null) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR.getCode(), "用户名已存在");
        }
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone());
        user.setNickname(req.getNickname());
        user.setRole(req.getRole());
        user.setStatus(1);
        userService.save(user);
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        return Result.ok(new LoginResponse(token, user.getId(), user.getUsername(), user.getRole()));
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        User user = userMapper.findByUsername(req.getUsername());
        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.UNAUTHORIZED.getCode(), "用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "账号已禁用");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        return Result.ok(new LoginResponse(token, user.getId(), user.getUsername(), user.getRole()));
    }

    @GetMapping("/me")
    public Result<UserPrincipal> me() {
        Long uid = SecurityUtil.currentUserId();
        if (uid == null) {
            return Result.fail(ResultCode.UNAUTHORIZED);
        }
        User user = userService.getById(uid);
        return Result.ok(new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.getStatus()));
    }
}