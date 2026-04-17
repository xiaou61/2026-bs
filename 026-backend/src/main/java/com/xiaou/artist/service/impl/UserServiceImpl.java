package com.xiaou.artist.service.impl;

import com.xiaou.artist.entity.User;
import com.xiaou.artist.mapper.UserMapper;
import com.xiaou.artist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return sanitizeUser(user);
        }
        throw new RuntimeException("用户名或密码错误");
    }

    @Override
    public User register(User user) {
        User existUser = userMapper.selectByUsername(user.getUsername());
        if (existUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (user.getRole() == null) {
            user.setRole("USER");
        }
        if (user.getStatus() == null) {
            user.setStatus("ACTIVE");
        }
        if (user.getBalance() == null) {
            user.setBalance(BigDecimal.ZERO);
        }
        if (user.getNickname() == null || user.getNickname().isBlank()) {
            user.setNickname(user.getUsername());
        }
        userMapper.insert(user);
        return sanitizeUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return sanitizeUser(userMapper.selectById(id));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userMapper.selectAll();
        List<User> sanitizedUsers = new ArrayList<>(users.size());
        for (User user : users) {
            sanitizedUsers.add(sanitizeUser(user));
        }
        return sanitizedUsers;
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.update(user) > 0;
    }

    @Override
    public boolean updatePassword(Long id, String password) {
        return userMapper.updatePassword(id, password) > 0;
    }

    @Override
    public boolean updateBalance(Long id, BigDecimal amount) {
        return userMapper.updateBalance(id, amount) > 0;
    }

    private User sanitizeUser(User source) {
        if (source == null) {
            return null;
        }
        User target = new User();
        target.setId(source.getId());
        target.setUsername(source.getUsername());
        target.setEmail(source.getEmail());
        target.setPhone(source.getPhone());
        target.setAvatar(source.getAvatar());
        target.setNickname(source.getNickname());
        target.setBio(source.getBio());
        target.setRole(source.getRole());
        target.setStatus(source.getStatus());
        target.setBalance(source.getBalance());
        target.setCreateTime(source.getCreateTime());
        target.setUpdateTime(source.getUpdateTime());
        return target;
    }
}
