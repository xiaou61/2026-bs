package com.diet.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diet.management.entity.User;
import com.diet.management.enums.Enums;
import com.diet.management.mapper.UserMapper;
import com.diet.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 用户服务实现
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;
    
    @Override
    public User getUserByUsername(String username) {
        return lambdaQuery().eq(User::getUsername, username).one();
    }
    
    @Override
    public boolean register(User user) {
        // 检查用户名是否存在
        if (getUserByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 设置默认值
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (user.getRole() == null) {
            user.setRole(Enums.UserRole.USER);
        }
        if (user.getStatus() == null) {
            user.setStatus(1);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return save(user);
    }
    
    @Override
    public String login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new IllegalArgumentException("账号已禁用");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }

        String payload = user.getId() + ":" + user.getUsername() + ":" + user.getRole().getCode();
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
    }
    
    @Override
    public boolean updateUserInfo(User user) {
        return updateById(user);
    }
}
