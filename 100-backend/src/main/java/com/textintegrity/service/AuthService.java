package com.textintegrity.service;

import com.textintegrity.common.BusinessException;
import com.textintegrity.mapper.CommonMapper;
import com.textintegrity.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AccessLogService accessLogService;

    public Map<String, Object> login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BusinessException(400, "用户名和密码不能为空");
        }
        Map<String, Object> user = commonMapper.findUserByUsername(username);
        if (user == null || !password.equals(user.get("password"))) {
            throw new BusinessException(400, "用户名或密码错误");
        }
        if (Integer.parseInt(String.valueOf(user.get("status"))) != 1) {
            throw new BusinessException(403, "账号已禁用");
        }
        Long userId = Long.valueOf(String.valueOf(user.get("id")));
        String role = String.valueOf(user.get("role"));
        String token = jwtUtils.generateToken(userId, role);
        tokenService.store(userId, token);
        user.remove("password");
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        accessLogService.record(userId, "认证", "登录", "用户登录学术诚信系统");
        return result;
    }

    public Map<String, Object> info(Long userId) {
        Map<String, Object> user = commonMapper.findUserById(userId);
        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }
        user.remove("password");
        return user;
    }

    public void logout(Long userId) {
        tokenService.remove(userId);
        accessLogService.record(userId, "认证", "退出", "用户退出系统");
    }

    public void assertAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException(403, "无权限操作");
        }
    }

    public void assertTeacher(String role) {
        if (!"ADMIN".equals(role) && !"TEACHER".equals(role)) {
            throw new BusinessException(403, "无权限操作");
        }
    }

    public void assertReviewer(String role) {
        if (!"ADMIN".equals(role) && !"REVIEWER".equals(role) && !"TEACHER".equals(role)) {
            throw new BusinessException(403, "无权限操作");
        }
    }

    public void assertStudent(String role) {
        if (!"ADMIN".equals(role) && !"TEACHER".equals(role) && !"REVIEWER".equals(role) && !"STUDENT".equals(role)) {
            throw new BusinessException(403, "无权限操作");
        }
    }
}

