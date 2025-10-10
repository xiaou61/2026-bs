package com.xiaou.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.xiaou.entity.User;
import com.xiaou.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用");
        }
        String md5Password = DigestUtil.md5Hex(password);
        if (!user.getPassword().equals(md5Password)) {
            throw new RuntimeException("密码错误");
        }
        user.setPassword(null);
        return user;
    }

    public void register(User user) {
        User existUser = userMapper.findByUsername(user.getUsername());
        if (existUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        user.setRole("user");
        user.setStatus(1);
        userMapper.insert(user);
    }

    public User getUserById(Long id) {
        User user = userMapper.findById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }

    public List<User> getAllUsers() {
        List<User> users = userMapper.findAll();
        users.forEach(u -> u.setPassword(null));
        return users;
    }

    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

    public void updateStatus(Long id, Integer status) {
        userMapper.updateStatus(id, status);
    }
}

