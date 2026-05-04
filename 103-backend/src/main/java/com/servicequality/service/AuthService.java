package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.SysUser;
import com.servicequality.mapper.SysUserMapper;
import com.servicequality.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private OperationLogService operationLogService;

    public Map<String, Object> login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BusinessException(400, "用户名和密码不能为空");
        }
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if (user == null || !password.equals(user.getPassword())) {
            throw new BusinessException(400, "用户名或密码错误");
        }
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BusinessException(403, "账号已禁用");
        }
        String token = jwtUtils.generateToken(user.getId(), user.getRole());
        tokenService.store(user.getId(), token);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        operationLogService.record(user.getId(), "认证", "登录", "用户登录客服质检系统");
        return result;
    }

    public SysUser info(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }
        return user;
    }

    public void logout(Long userId) {
        tokenService.remove(userId);
        operationLogService.record(userId, "认证", "退出", "用户退出系统");
    }

    public void assertAdmin(String role) {
        if (!"ADMIN".equals(role)) throw new BusinessException(403, "无权限操作");
    }

    public void assertSupervisor(String role) {
        if (!"ADMIN".equals(role) && !"SUPERVISOR".equals(role)) throw new BusinessException(403, "无权限操作");
    }

    public void assertQa(String role) {
        if (!"ADMIN".equals(role) && !"SUPERVISOR".equals(role) && !"QA".equals(role)) throw new BusinessException(403, "无权限操作");
    }

    public void assertStaff(String role) {
        if (!"ADMIN".equals(role) && !"SUPERVISOR".equals(role) && !"AGENT".equals(role) && !"QA".equals(role)) throw new BusinessException(403, "无权限操作");
    }
}
