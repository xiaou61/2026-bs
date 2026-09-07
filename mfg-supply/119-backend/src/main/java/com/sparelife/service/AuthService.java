package com.sparelife.service;

import com.sparelife.common.BusinessException;
import com.sparelife.dto.LoginRequest;
import com.sparelife.entity.SysUser;
import com.sparelife.mapper.SysUserMapper;
import com.sparelife.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, request.getUsername()));
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

    public void assertAdminOrDeviceAdmin(String role) {
        assertAny(role, "ADMIN", "DEVICE_ADMIN");
    }

    public void assertAdminOrMaintainer(String role) {
        assertAny(role, "ADMIN", "MAINTAINER");
    }

    public void assertAdminOrAnalyst(String role) {
        assertAny(role, "ADMIN", "ANALYST");
    }

    public void assertAdminOrDeviceAdminOrMaintainer(String role) {
        assertAny(role, "ADMIN", "DEVICE_ADMIN", "MAINTAINER");
    }

    public void assertAdminOrDeviceAdminOrAnalyst(String role) {
        assertAny(role, "ADMIN", "DEVICE_ADMIN", "ANALYST");
    }

    public void assertAuthenticated(String role) {
        assertAny(role, "ADMIN", "DEVICE_ADMIN", "MAINTAINER", "ANALYST");
    }

    private void assertAny(String role, String... allowedRoles) {
        if (role == null) throw new BusinessException("无权限操作");
        for (String allowedRole : allowedRoles) {
            if (allowedRole.equals(role)) return;
        }
        throw new BusinessException("无权限操作");
    }
}
