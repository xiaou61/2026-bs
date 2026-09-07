package com.schoolbus.clerk;

import com.schoolbus.common.BusinessException;
import com.schoolbus.dto.LoginRequest;
import com.schoolbus.entity.SysUser;
import com.schoolbus.mapper.SysUserMapper;
import com.schoolbus.utils.JwtUtils;
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
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return data;
    }

    public SysUser info(String token) {
        Claims claims = JwtUtils.parse(clean(token));
        return userMapper.selectById(Long.valueOf(claims.getSubject()));
    }

    public void logout(String token) {
        if (token != null) tokenService.remove(clean(token));
    }

    public void assertAdmin(String role) {
        assertAnyRole(role, "ADMIN");
    }

    public void assertAuthenticated(String role) {
        if (!StringUtils.hasText(role)) throw new BusinessException("无权限访问");
    }

    public void assertAnyRole(String role, String... roles) {
        assertAuthenticated(role);
        for (String item : roles) if (item.equals(role)) return;
        throw new BusinessException("无权限访问");
    }

    private String clean(String token) {
        if (token != null && token.startsWith("Bearer ")) return token.substring(7);
        return token;
    }
}
