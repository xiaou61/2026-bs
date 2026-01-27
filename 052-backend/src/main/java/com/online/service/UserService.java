package com.online.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.entity.User;
import com.online.mapper.UserMapper;
import com.online.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, Object> register(String username, String password, String nickname) {
        User exist = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (exist != null) {
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(DigestUtil.md5Hex(password));
        user.setNickname(nickname != null ? nickname : username);
        user.setRole(0);
        user.setStatus(1);
        this.save(user);

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    public Map<String, Object> login(String username, String password) {
        User user = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .eq(User::getPassword, DigestUtil.md5Hex(password)));
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        if (user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        user.setPassword(null);
        result.put("user", user);
        return result;
    }

    public User getUserInfo(Long userId) {
        User user = this.getById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public void updateUserInfo(Long userId, User user) {
        user.setId(userId);
        user.setPassword(null);
        user.setRole(null);
        user.setStatus(null);
        this.updateById(user);
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = this.getById(userId);
        if (!user.getPassword().equals(DigestUtil.md5Hex(oldPassword))) {
            throw new RuntimeException("原密码错误");
        }
        user.setPassword(DigestUtil.md5Hex(newPassword));
        this.updateById(user);
    }
}
