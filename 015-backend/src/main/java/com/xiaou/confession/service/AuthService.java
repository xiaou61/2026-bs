package com.xiaou.confession.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.confession.entity.Admin;
import com.xiaou.confession.entity.User;
import com.xiaou.confession.mapper.AdminMapper;
import com.xiaou.confession.mapper.UserMapper;
import com.xiaou.confession.util.JwtUtil;
import com.xiaou.confession.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final JwtUtil jwtUtil;
    
    public Map<String, Object> userRegister(String studentId, String realName, String phone, 
                                            String password, String school, String college, Integer grade) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStudentId, studentId);
        if (userMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("学号已存在");
        }
        
        wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        if (userMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("手机号已注册");
        }
        
        User user = new User();
        user.setStudentId(studentId);
        user.setRealName(realName);
        user.setPhone(phone);
        user.setPassword(PasswordUtil.encode(password));
        user.setSchool(school);
        user.setCollege(college);
        user.setGrade(grade);
        user.setAuthStatus(0);
        user.setPoints(10);
        user.setLevel(1);
        user.setPostCount(0);
        user.setCommentCount(0);
        user.setLikeCount(0);
        user.setStatus(0);
        user.setViolationCount(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        userMapper.insert(user);
        
        String token = jwtUtil.generateToken(user.getId(), "USER");
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        
        return result;
    }
    
    public Map<String, Object> userLogin(String account, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStudentId, account).or().eq(User::getPhone, account);
        User user = userMapper.selectOne(wrapper);
        
        if (user == null) {
            throw new RuntimeException("账号不存在");
        }
        
        if (!PasswordUtil.matches(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        
        if (user.getStatus() == 2) {
            throw new RuntimeException("账号已被封禁");
        }
        
        if (user.getStatus() == 1 && user.getBanEndTime() != null 
                && user.getBanEndTime().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("账号禁言中，解禁时间：" + user.getBanEndTime());
        }
        
        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateById(user);
        
        user.setPassword(null);
        
        String token = jwtUtil.generateToken(user.getId(), "USER");
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        
        return result;
    }
    
    public Map<String, Object> adminLogin(String username, String password) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, username);
        Admin admin = adminMapper.selectOne(wrapper);
        
        if (admin == null) {
            throw new RuntimeException("管理员不存在");
        }
        
        if (!PasswordUtil.matches(password, admin.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        
        admin.setPassword(null);
        
        String token = jwtUtil.generateToken(admin.getId(), "ADMIN");
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("admin", admin);
        
        return result;
    }
}

