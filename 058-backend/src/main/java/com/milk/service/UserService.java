package com.milk.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.common.BusinessException;
import com.milk.entity.User;
import com.milk.mapper.UserMapper;
import com.milk.utils.JwtUtils;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate<String, Object> redisTemplate;

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
        String token = JwtUtils.generateToken(String.valueOf(user.getId()), user.getRole());
        redisTemplate.opsForValue().set("user:token:" + user.getId(), token, 24, TimeUnit.HOURS);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user);
        user.setPassword(null);
        return map;
    }

    public User register(User user) {
        User exist = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (exist != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setRole("USER");
        user.setStatus(1);
        userMapper.insert(user);
        user.setPassword(null);
        return user;
    }

    public User getById(Long id) {
        User user = userMapper.selectById(id);
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

    public void save(User user) {
        if (user.getId() == null) {
            User exist = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
            if (exist != null) {
                throw new BusinessException("用户名已存在");
            }
            userMapper.insert(user);
        } else {
            userMapper.updateById(user);
        }
    }

    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }
}
