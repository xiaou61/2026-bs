package com.xiaou.express.service;

import com.xiaou.express.entity.User;
import com.xiaou.express.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }

    public void updateUser(User user) {
        userMapper.updateById(user);
    }
}

