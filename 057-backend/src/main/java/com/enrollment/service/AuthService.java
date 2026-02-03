package com.enrollment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.enrollment.common.BusinessException;
import com.enrollment.entity.Admin;
import com.enrollment.mapper.AdminMapper;
import com.enrollment.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Map<String, Object> login(String username, String password) {
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
        if (admin == null) {
            throw new BusinessException("用户不存在");
        }
        if (!admin.getPassword().equals(password)) {
            throw new BusinessException("密码错误");
        }
        if (admin.getStatus() != 1) {
            throw new BusinessException("账号已被禁用");
        }
        String token = jwtUtils.generateToken(admin.getId().toString());
        redisTemplate.opsForValue().set("token:" + admin.getId(), token, 24, TimeUnit.HOURS);
        admin.setPassword(null);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("admin", admin);
        return result;
    }

    public Admin getAdminById(Long id) {
        Admin admin = adminMapper.selectById(id);
        if (admin != null) {
            admin.setPassword(null);
        }
        return admin;
    }

    public void logout(Long adminId) {
        redisTemplate.delete("token:" + adminId);
    }

    public void updatePassword(Long adminId, String oldPassword, String newPassword) {
        Admin admin = adminMapper.selectById(adminId);
        if (!admin.getPassword().equals(oldPassword)) {
            throw new BusinessException("原密码错误");
        }
        admin.setPassword(newPassword);
        adminMapper.updateById(admin);
    }
}
