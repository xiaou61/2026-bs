package com.xiaou.collabboard.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.collabboard.entity.User;
import com.xiaou.collabboard.mapper.UserMapper;
import com.xiaou.collabboard.util.MD5Util;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    public User register(String username, String password, String nickname, String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        if (baseMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("用户名已存在");
        }

        if (email != null) {
            wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getEmail, email);
            if (baseMapper.selectCount(wrapper) > 0) {
                throw new RuntimeException("邮箱已被注册");
            }
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Util.encrypt(password));
        user.setNickname(nickname);
        user.setEmail(email);
        user.setRole("USER");
        user.setStatus(1);
        user.setDocCount(0);
        user.setCollabCount(0);
        user.setTeamCount(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        baseMapper.insert(user);
        return user;
    }

    public User login(String username, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username)
                .eq(User::getPassword, MD5Util.encrypt(password));
        User user = baseMapper.selectOne(wrapper);

        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        if (user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用");
        }

        user.setLastLoginTime(LocalDateTime.now());
        baseMapper.updateById(user);

        return user;
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = baseMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (!user.getPassword().equals(MD5Util.encrypt(oldPassword))) {
            throw new RuntimeException("原密码错误");
        }

        user.setPassword(MD5Util.encrypt(newPassword));
        user.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(user);
    }

    public void incrementDocCount(Long userId) {
        User user = baseMapper.selectById(userId);
        if (user != null) {
            user.setDocCount(user.getDocCount() + 1);
            baseMapper.updateById(user);
        }
    }

    public void decrementDocCount(Long userId) {
        User user = baseMapper.selectById(userId);
        if (user != null && user.getDocCount() > 0) {
            user.setDocCount(user.getDocCount() - 1);
            baseMapper.updateById(user);
        }
    }
}

