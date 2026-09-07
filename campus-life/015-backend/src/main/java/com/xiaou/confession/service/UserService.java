package com.xiaou.confession.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.confession.entity.User;
import com.xiaou.confession.mapper.UserMapper;
import com.xiaou.confession.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserMapper userMapper;
    
    public User getUserById(Long userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }
    
    @Transactional
    public void submitAuth(Long userId, String authImage) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        user.setAuthImage(authImage);
        user.setAuthStatus(1);
        user.setUpdateTime(LocalDateTime.now());
        
        userMapper.updateById(user);
    }
    
    @Transactional
    public void updateProfile(Long userId, String school, String college, Integer grade) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (school != null) {
            user.setSchool(school);
        }
        if (college != null) {
            user.setCollege(college);
        }
        if (grade != null) {
            user.setGrade(grade);
        }
        
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }
    
    @Transactional
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (!PasswordUtil.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        
        user.setPassword(PasswordUtil.encode(newPassword));
        user.setUpdateTime(LocalDateTime.now());
        
        userMapper.updateById(user);
    }
}

