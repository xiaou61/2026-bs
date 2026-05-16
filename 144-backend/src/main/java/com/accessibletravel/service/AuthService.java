package com.accessibletravel.service;

import com.accessibletravel.common.BusinessException;
import com.accessibletravel.dto.LoginRequest;
import com.accessibletravel.entity.SysUser;
import com.accessibletravel.mapper.SysUserMapper;
import com.accessibletravel.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public void assertAdmin(String role) {
        assertAny(role, "ADMIN");
    }

    public void assertAdminOrDispatcher(String role) {
        assertAny(role, "ADMIN", "DISPATCHER");
    }

    public void assertAdminOrTraveler(String role) {
        assertAny(role, "ADMIN", "TRAVELER");
    }

    public void assertAdminOrVolunteer(String role) {
        assertAny(role, "ADMIN", "VOLUNTEER");
    }

    public void assertAdminOrDispatcherOrVolunteer(String role) {
        assertAny(role, "ADMIN", "DISPATCHER", "VOLUNTEER");
    }

    public void assertAdminOrTravelerOrDispatcher(String role) {
        assertAny(role, "ADMIN", "TRAVELER", "DISPATCHER");
    }

    public void assertAdminOrTravelerOrDispatcherOrVolunteer(String role) {
        assertAny(role, "ADMIN", "TRAVELER", "DISPATCHER", "VOLUNTEER");
    }

    public void assertAuthenticated(String role) {
        if (!StringUtils.hasText(role)) throw new BusinessException("无权限访问");
    }

    private String clean(String token) {
        if (token != null && token.startsWith("Bearer ")) return token.substring(7);
        return token;
    }

    private void assertAny(String role, String... roles) {
        if (!StringUtils.hasText(role)) throw new BusinessException("无权限访问");
        for (String item : roles) {
            if (item.equals(role)) return;
        }
        throw new BusinessException("无权限访问");
    }
}
