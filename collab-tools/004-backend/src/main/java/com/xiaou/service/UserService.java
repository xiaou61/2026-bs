package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.User;
import com.xiaou.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    
    public User getByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
    }
    
    public List<User> searchUsers(String keyword) {
        List<User> users = this.list(new LambdaQueryWrapper<User>()
                .like(User::getUsername, keyword)
                .or()
                .like(User::getNickname, keyword)
                .eq(User::getStatus, 1)
                .last("limit 10"));
        
        users.forEach(user -> user.setPassword(null));
        return users;
    }
}

