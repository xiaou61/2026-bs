package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.dto.LoginDTO;
import com.xiaou.entity.User;
import com.xiaou.service.UserService;
import com.xiaou.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO dto) {
        User user = userService.login(dto.getUsername(), dto.getPassword());
        if (user == null) {
            return Result.error("用户名或密码错误");
        }
        
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return Result.success(data);
    }

    @PostMapping("/wxLogin")
    public Result<?> wxLogin(@RequestParam String openid) {
        User user = userService.wxLogin(openid);
        if (user == null) {
            return Result.error("用户未绑定，请先绑定账号");
        }
        
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return Result.success(data);
    }

    @PostMapping("/bindWx")
    public Result<?> bindWx(@RequestParam String openid, @RequestBody LoginDTO dto) {
        User user = userService.login(dto.getUsername(), dto.getPassword());
        if (user == null) {
            return Result.error("用户名或密码错误");
        }
        
        user.setOpenid(openid);
        userService.updateById(user);
        
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return Result.success(data);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        // 检查用户名是否已存在
        if (userService.lambdaQuery().eq(User::getUsername, user.getUsername()).count() > 0) {
            return Result.error("用户名已存在");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        userService.save(user);
        return Result.success("注册成功");
    }
}
