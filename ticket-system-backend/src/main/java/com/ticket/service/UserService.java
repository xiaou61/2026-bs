package com.ticket.service;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.dto.LoginDTO;
import com.ticket.dto.RegisterDTO;
import com.ticket.entity.User;
import com.ticket.mapper.UserMapper;
import com.ticket.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    public Map<String, Object> register(RegisterDTO dto) {
        User existUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", dto.getUsername()));
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(BCrypt.hashpw(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setNickname(dto.getNickname());
        user.setBalance(BigDecimal.ZERO);
        user.setRole("user");
        user.setStatus(1);

        userMapper.insert(user);

        String token = jwtUtils.generateToken(user.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        user.setPassword(null);
        result.put("userInfo", user);
        return result;
    }

    public Map<String, Object> login(LoginDTO dto) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", dto.getUsername()));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }

        String token = jwtUtils.generateToken(user.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        user.setPassword(null);
        result.put("userInfo", user);
        return result;
    }

    public User getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setPassword(null);
        return user;
    }

    public void updateUser(User user) {
        user.setPassword(null);
        userMapper.updateById(user);
    }

    public Page<User> listUsers(Integer pageNum, Integer pageSize, String username, String phone) {
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (username != null && !username.isEmpty()) {
            wrapper.like("username", username);
        }
        if (phone != null && !phone.isEmpty()) {
            wrapper.like("phone", phone);
        }
        wrapper.orderByDesc("create_time");
        Page<User> result = userMapper.selectPage(page, wrapper);
        result.getRecords().forEach(user -> user.setPassword(null));
        return result;
    }

    public void updateStatus(Long id, Integer status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        userMapper.updateById(user);
    }

    public void recharge(Long userId, BigDecimal amount) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setBalance(user.getBalance().add(amount));
        userMapper.updateById(user);
    }
}
