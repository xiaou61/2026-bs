package com.xiaou.resource.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.resource.entity.User;
import com.xiaou.resource.mapper.UserMapper;
import com.xiaou.resource.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, Object> register(User user) {
        Map<String, Object> result = new HashMap<>();
        
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        if (this.getOne(wrapper) != null) {
            result.put("success", false);
            result.put("message", "用户名已存在");
            return result;
        }

        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        user.setRole("student");
        user.setPoints(100);
        user.setLevel(1);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        this.save(user);

        result.put("success", true);
        result.put("message", "注册成功");
        return result;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> result = new HashMap<>();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = this.getOne(wrapper);

        if (user == null) {
            result.put("success", false);
            result.put("message", "用户不存在");
            return result;
        }

        if (!user.getPassword().equals(DigestUtil.md5Hex(password))) {
            result.put("success", false);
            result.put("message", "密码错误");
            return result;
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        result.put("success", true);
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    public User getUserInfo(Long userId) {
        return this.getById(userId);
    }

    public boolean updateUserInfo(User user) {
        user.setUpdateTime(LocalDateTime.now());
        return this.updateById(user);
    }

    public boolean addPoints(Long userId, Integer points) {
        User user = this.getById(userId);
        if (user != null) {
            user.setPoints(user.getPoints() + points);
            user.setUpdateTime(LocalDateTime.now());
            return this.updateById(user);
        }
        return false;
    }

    public boolean deductPoints(Long userId, Integer points) {
        User user = this.getById(userId);
        if (user != null && user.getPoints() >= points) {
            user.setPoints(user.getPoints() - points);
            user.setUpdateTime(LocalDateTime.now());
            return this.updateById(user);
        }
        return false;
    }
}

