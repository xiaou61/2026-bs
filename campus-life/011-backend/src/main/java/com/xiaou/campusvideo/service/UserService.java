package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.User;
import com.xiaou.campusvideo.mapper.UserMapper;
import com.xiaou.campusvideo.util.MD5Util;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    
    public User login(String username, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username)
               .eq(User::getPassword, MD5Util.encrypt(password));
        return this.getOne(wrapper);
    }
    
    public User register(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        if (this.getOne(wrapper) != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        user.setLevel(1);
        user.setPoints(0);
        user.setLikeCount(0);
        user.setFansCount(0);
        user.setFollowCount(0);
        user.setVideoCount(0);
        user.setRole("USER");
        user.setStatus(1);
        
        this.save(user);
        return user;
    }
    
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return this.getOne(wrapper);
    }
    
    public void updatePoints(Long userId, Integer points) {
        User user = this.getById(userId);
        if (user != null) {
            user.setPoints(user.getPoints() + points);
            
            if (user.getPoints() >= 5000) {
                user.setLevel(5);
            } else if (user.getPoints() >= 2000) {
                user.setLevel(4);
            } else if (user.getPoints() >= 500) {
                user.setLevel(3);
            } else if (user.getPoints() >= 100) {
                user.setLevel(2);
            } else {
                user.setLevel(1);
            }
            
            this.updateById(user);
        }
    }
}

