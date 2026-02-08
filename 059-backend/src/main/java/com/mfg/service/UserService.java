package com.mfg.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mfg.common.BusinessException;
import com.mfg.entity.User;
import com.mfg.mapper.UserMapper;
import com.mfg.utils.JwtUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public Map<String, Object> login(String username, String password) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        String token = JwtUtils.generateToken(String.valueOf(user.getId()));
        stringRedisTemplate.opsForValue().set("token:" + user.getId(), token, 24, TimeUnit.HOURS);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        user.setPassword(null);
        return result;
    }

    public void logout(Long userId) {
        stringRedisTemplate.delete("token:" + userId);
    }

    public User getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public Page<User> page(Integer pageNum, Integer pageSize, String username, String role) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(username)) {
            wrapper.like("username", username);
        }
        if (StrUtil.isNotBlank(role)) {
            wrapper.eq("role", role);
        }
        wrapper.orderByDesc("create_time");
        Page<User> page = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        page.getRecords().forEach(u -> u.setPassword(null));
        return page;
    }

    public void add(User user) {
        User exist = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (exist != null) {
            throw new BusinessException("用户名已存在");
        }
        userMapper.insert(user);
    }

    public void update(User user) {
        user.setPassword(null);
        userMapper.updateById(user);
    }

    public void delete(Long id) {
        userMapper.deleteById(id);
    }
}
