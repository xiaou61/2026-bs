package com.groupbuy.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.groupbuy.common.BusinessException;
import com.groupbuy.entity.User;
import com.groupbuy.mapper.UserMapper;
import com.groupbuy.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RuntimeStoreService runtimeStoreService;

    public void register(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new BusinessException("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new BusinessException("密码不能为空");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("用户名已存在");
        }
        user.setRole(2);
        user.setStatus(1);
        userMapper.insert(user);
    }

    public Map<String, Object> login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        String token = jwtUtils.generateToken(user.getId(), user.getRole());
        runtimeStoreService.storeToken(user.getId(), token);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", safeUser(user));
        return result;
    }

    public User getInfo(Long userId) {
        User user = userMapper.selectById(userId);
        return safeUser(user);
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (!oldPassword.equals(user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        user.setPassword(newPassword);
        userMapper.updateById(user);
    }

    public void logout(Long userId) {
        runtimeStoreService.invalidateToken(userId);
    }

    private User safeUser(User user) {
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }
}
