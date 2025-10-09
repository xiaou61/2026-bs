package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.User;
import com.xiaou.mapper.UserMapper;
import com.xiaou.service.UserService;
import com.xiaou.utils.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User login(String username, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = this.getOne(wrapper);

        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }

        if (!PasswordUtil.matches(password, user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        return user;
    }

    @Override
    public void register(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        if (this.getOne(wrapper) != null) {
            throw new BusinessException("用户名已存在");
        }

        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setStatus(1);
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("buyer");
        }

        this.save(user);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        if (!PasswordUtil.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("原密码错误");
        }

        user.setPassword(PasswordUtil.encode(newPassword));
        this.updateById(user);
    }
}

