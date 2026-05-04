package com.alumni.service;

import com.alumni.common.BusinessException;
import com.alumni.entity.User;
import com.alumni.mapper.UserMapper;
import com.alumni.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public Map<String, Object> login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BusinessException("用户名和密码不能为空");
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException("账号待审核");
        }
        if (user.getStatus() == 2) {
            throw new BusinessException("账号已禁用");
        }
        String token = jwtUtils.generateToken(user.getId(), user.getUsername(), user.getRole());
        runtimeStoreService.activateToken(token);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    public Long register(User user) {
        if (user == null || !StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }
        User exist = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername()));
        if (exist != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setRole("alumni");
        user.setStatus(0);
        userMapper.insert(user);
        return user.getId();
    }

    public User getInfo(Long userId) {
        return userMapper.selectById(userId);
    }

    public void logout(String token) {
        runtimeStoreService.invalidateToken(token);
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        if (!StringUtils.hasText(oldPassword) || !StringUtils.hasText(newPassword)) {
            throw new BusinessException("密码不能为空");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        if (!user.getPassword().equals(oldPassword)) {
            throw new BusinessException("原密码错误");
        }
        user.setPassword(newPassword);
        userMapper.updateById(user);
    }

    public void updateInfo(User user) {
        User update = new User();
        update.setId(user.getId());
        update.setName(user.getName());
        update.setAvatar(user.getAvatar());
        update.setPhone(user.getPhone());
        update.setEmail(user.getEmail());
        userMapper.updateById(update);
    }
}
