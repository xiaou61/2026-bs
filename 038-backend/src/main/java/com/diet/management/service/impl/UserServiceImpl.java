package com.diet.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diet.management.entity.User;
import com.diet.management.enums.Enums;
import com.diet.management.mapper.UserMapper;
import com.diet.management.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
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
        if (user.getRole() == null) {
            user.setRole(Enums.UserRole.USER);
        }
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        
        // TODO: 密码加密
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return save(user);
    }
    
    @Override
    public String login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // TODO: 密码验证和JWT生成
        // if (!passwordEncoder.matches(password, user.getPassword())) {
        //     throw new RuntimeException("密码错误");
        // }
        // return jwtUtil.generateToken(user.getId(), user.getUsername());
        
        return "token_placeholder";
    }
    
    @Override
    public boolean updateUserInfo(User user) {
        return updateById(user);
    }
}
