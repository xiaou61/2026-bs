package com.vehicleclaim.service;

import com.vehicleclaim.common.BusinessException;
import com.vehicleclaim.dto.LoginRequest;
import com.vehicleclaim.entity.SysUser;
import com.vehicleclaim.mapper.SysUserMapper;
import com.vehicleclaim.utils.JwtUtils;
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

    public void assertAdminOrLegal(String role) {
        assertAny(role, "ADMIN", "LEGAL");
    }

    public void assertAdminOrApplicant(String role) {
        assertAny(role, "ADMIN", "APPLICANT");
    }

    public void assertAdminOrApprover(String role) {
        assertAny(role, "ADMIN", "APPROVER");
    }

    public void assertAdminOrLegalOrApplicant(String role) {
        assertAny(role, "ADMIN", "LEGAL", "APPLICANT");
    }

    public void assertAdminOrLegalOrApprover(String role) {
        assertAny(role, "ADMIN", "LEGAL", "APPROVER");
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
