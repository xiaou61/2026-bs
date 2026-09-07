package com.xiaou.service;

import com.xiaou.common.BusinessException;
import com.xiaou.entity.User;
import com.xiaou.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findById(Long id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public List<User> findAll(String role, String keyword) {
        return userMapper.findAll(role, keyword);
    }

    public User create(User user) {
        User existUser = userMapper.findByUsername(user.getUsername());
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }
        userMapper.insert(user);
        return user;
    }

    public User update(User user) {
        User existUser = userMapper.findById(user.getId());
        if (existUser == null) {
            throw new BusinessException("用户不存在");
        }
        userMapper.update(user);
        return userMapper.findById(user.getId());
    }

    public void delete(Long id) {
        User existUser = userMapper.findById(id);
        if (existUser == null) {
            throw new BusinessException("用户不存在");
        }
        userMapper.deleteById(id);
    }
}

