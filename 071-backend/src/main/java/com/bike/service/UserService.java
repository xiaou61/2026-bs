package com.bike.service;

import cn.hutool.core.util.StrUtil;
import com.bike.common.BusinessException;
import com.bike.entity.User;
import com.bike.mapper.UserMapper;
import com.bike.utils.JwtUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Map<String, Object> login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() != 1) {
            throw new BusinessException("账号已被禁用");
        }
        String token = jwtUtils.generateToken(String.valueOf(user.getId()), user.getRole());
        redisTemplate.opsForValue().set("user:token:" + user.getId(), token, 24, TimeUnit.HOURS);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        user.setPassword(null);
        result.put("user", user);
        return result;
    }

    public void register(User user) {
        User exist = userMapper.findByUsername(user.getUsername());
        if (exist != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setRole("user");
        user.setCreditScore(100);
        user.setBalance(BigDecimal.ZERO);
        user.setDepositPaid(0);
        user.setStatus(1);
        userMapper.insert(user);
    }

    public User getInfo(Long userId) {
        User user = userMapper.findById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public void updateInfo(User user) {
        userMapper.update(user);
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.findById(userId);
        if (!user.getPassword().equals(oldPassword)) {
            throw new BusinessException("原密码错误");
        }
        userMapper.updatePassword(userId, newPassword);
    }

    public PageInfo<User> getList(Integer pageNum, Integer pageSize, String username, String role, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(userMapper.findList(username, role, status));
    }

    public void updateStatus(Long id, Integer status) {
        userMapper.updateStatus(id, status);
    }
}
