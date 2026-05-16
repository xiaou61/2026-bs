package com.homeenergy.service;

import com.homeenergy.common.BusinessException;
import com.homeenergy.dto.LoginRequest;
import com.homeenergy.entity.SysUser;
import com.homeenergy.mapper.SysUserMapper;
import com.homeenergy.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final SysUserMapper userMapper;
    private final TokenService tokenService;

    public Map<String, Object> login(LoginRequest request) {
        SysUser user = userMapper.selectByUsername(request.getUsername());
        if (user == null || !user.getPassword().equals(request.getPassword())) throw new BusinessException("账号或密码错误");
        if (user.getStatus() == null || user.getStatus() != 1) throw new BusinessException("账号已停用");
        String token = JwtUtils.generateToken(user.getId(), user.getUsername(), user.getRole());
        tokenService.save(token, String.valueOf(user.getId()));
        user.setPassword(null);
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return data;
    }

    public SysUser info(String token) {
        Claims claims = JwtUtils.parse(clean(token));
        SysUser user = userMapper.selectById(Long.valueOf(claims.getSubject()));
        if (user != null) user.setPassword(null);
        return user;
    }

    public void logout(String token) {
        if (token != null) tokenService.remove(clean(token));
    }

    private String clean(String token) {
        if (token != null && token.startsWith("Bearer ")) return token.substring(7);
        return token;
    }

    public void assertAdmin(String role) {
        assertAny(role, "ADMIN");
    }

    public void assertAdminOrResident(String role) {
        assertAny(role, "ADMIN", "RESIDENT");
    }

    public void assertAdminOrAnalyst(String role) {
        assertAny(role, "ADMIN", "ANALYST");
    }

    public void assertAdminOrMaintainer(String role) {
        assertAny(role, "ADMIN", "MAINTAINER");
    }

    public void assertAdminOrResidentOrAnalyst(String role) {
        assertAny(role, "ADMIN", "RESIDENT", "ANALYST");
    }

    public void assertAdminOrResidentOrMaintainer(String role) {
        assertAny(role, "ADMIN", "RESIDENT", "MAINTAINER");
    }

    public void assertAdminOrAnalystOrMaintainer(String role) {
        assertAny(role, "ADMIN", "ANALYST", "MAINTAINER");
    }

    public void assertAdminOrResidentOrAnalystOrMaintainer(String role) {
        assertAny(role, "ADMIN", "RESIDENT", "ANALYST", "MAINTAINER");
    }

    public void assertAuthenticated(String role) {
        assertAdminOrResidentOrAnalystOrMaintainer(role);
    }

    private void assertAny(String role, String... allowedRoles) {
        if (role == null) throw new BusinessException("无权限操作");
        for (String allowedRole : allowedRoles) {
            if (allowedRole.equals(role)) return;
        }
        throw new BusinessException("无权限操作");
    }
}
