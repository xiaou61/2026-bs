package com.agriculture.service;

import com.agriculture.common.BusinessException;
import com.agriculture.dto.LoginDTO;
import com.agriculture.entity.User;
import com.agriculture.mapper.UserMapper;
import com.agriculture.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private JwtUtils jwtUtils;

    public String login(LoginDTO dto) {
        User user = this.lambdaQuery()
                .eq(User::getUsername, dto.getUsername())
                .eq(User::getPassword, dto.getPassword())
                .one();
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        return jwtUtils.generateToken(user.getId().toString());
    }

    public User getUserInfo(Long userId) {
        User user = this.getById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public Page<User> getUserPage(Integer pageNum, Integer pageSize, String username, String role) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (username != null && !username.isEmpty()) {
            wrapper.like(User::getUsername, username);
        }
        if (role != null && !role.isEmpty()) {
            wrapper.eq(User::getRole, role);
        }
        return this.page(page, wrapper);
    }
}
