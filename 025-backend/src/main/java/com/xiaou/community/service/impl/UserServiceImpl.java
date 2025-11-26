package com.xiaou.community.service.impl;

import com.xiaou.community.entity.User;
import com.xiaou.community.mapper.UserMapper;
import com.xiaou.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public void register(User user) {
        if (userMapper.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        userMapper.insert(user);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public void updatePassword(Integer id, String newPassword) {
        User user = new User();
        user.setId(id);
        user.setPassword(newPassword);
        userMapper.update(user);
    }

    @Override
    public List<User> getAll() {
        return userMapper.findAll();
    }
}
