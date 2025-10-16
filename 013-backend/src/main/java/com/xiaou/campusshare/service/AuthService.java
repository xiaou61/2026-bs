package com.xiaou.campusshare.service;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.campusshare.entity.User;
import com.xiaou.campusshare.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, Object> register(String username, String password, String nickname, String phone, String school) {
        Map<String, Object> result = new HashMap<>();

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        if (userService.count(wrapper) > 0) {
            result.put("success", false);
            result.put("message", "用户名已存在");
            return result;
        }

        wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        if (userService.count(wrapper) > 0) {
            result.put("success", false);
            result.put("message", "手机号已注册");
            return result;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(BCrypt.hashpw(password));
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setSchool(school);
        user.setAuthStatus(0);
        user.setCreditScore(100);
        user.setDepositBalance(BigDecimal.ZERO);
        user.setAccountBalance(BigDecimal.ZERO);
        user.setTotalOrders(0);
        user.setTotalIncome(BigDecimal.ZERO);
        user.setRole("USER");
        user.setStatus(1);

        userService.save(user);

        result.put("success", true);
        result.put("message", "注册成功");
        return result;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> result = new HashMap<>();

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userService.getOne(wrapper);

        if (user == null) {
            result.put("success", false);
            result.put("message", "用户不存在");
            return result;
        }

        if (user.getStatus() == 0) {
            result.put("success", false);
            result.put("message", "账号已被禁用");
            return result;
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误");
            return result;
        }

        user.setLastLoginTime(LocalDateTime.now());
        userService.updateById(user);

        String token = jwtUtil.generateToken(user.getId());
        user.setPassword(null);

        result.put("success", true);
        result.put("message", "登录成功");
        result.put("token", token);
        result.put("user", user);
        return result;
    }
}

