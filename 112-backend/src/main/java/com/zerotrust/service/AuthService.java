package com.zerotrust.service;

import com.zerotrust.common.BusinessException;
import com.zerotrust.dto.LoginRequest;
import com.zerotrust.entity.SysUser;
import com.zerotrust.mapper.SysUserMapper;
import com.zerotrust.utils.JwtUtils;
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
        SysUser user = userMapper.findByUsername(request.getUsername());
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            throw new BusinessException("账号或密码错误");
        }
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BusinessException("账号已停用");
        }
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
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public void logout(String token) {
        if (token != null) {
            tokenService.remove(clean(token));
        }
    }

    private String clean(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    public void assertAdmin(String role) {
        assertAny(role, "ADMIN");
    }

    public void assertAdminOrSecurity(String role) {
        assertAny(role, "ADMIN", "SECURITY");
    }

    public void assertAdminOrNetwork(String role) {
        assertAny(role, "ADMIN", "NETWORK");
    }

    public void assertAdminOrAuditor(String role) {
        assertAny(role, "ADMIN", "AUDITOR");
    }

    public void assertAdminOrSecurityOrAuditor(String role) {
        assertAny(role, "ADMIN", "SECURITY", "AUDITOR");
    }

    public void assertAdminOrNetworkOrAuditor(String role) {
        assertAny(role, "ADMIN", "NETWORK", "AUDITOR");
    }

    public void assertAuthenticated(String role) {
        assertAny(role, "ADMIN", "SECURITY", "NETWORK", "AUDITOR");
    }

    private void assertAny(String role, String... allowedRoles) {
        if (role == null) {
            throw new BusinessException("无权限操作");
        }
        for (String allowedRole : allowedRoles) {
            if (allowedRole.equals(role)) {
                return;
            }
        }
        throw new BusinessException("无权限操作");
    }
}
