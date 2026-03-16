package com.wallpaper.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wallpaper.common.BusinessException;
import com.wallpaper.dto.LoginDTO;
import com.wallpaper.dto.RegisterDTO;
import com.wallpaper.entity.SysUser;
import com.wallpaper.mapper.SysUserMapper;
import com.wallpaper.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Map<String, Object> login(LoginDTO loginDTO) {
        if (!StringUtils.hasText(loginDTO.getUsername()) || !StringUtils.hasText(loginDTO.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, loginDTO.getUsername())
                .eq(SysUser::getPassword, loginDTO.getPassword())
                .last("limit 1");
        SysUser user = sysUserMapper.selectOne(wrapper);
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException("账号已禁用");
        }
        String token = jwtUtils.generateToken(String.valueOf(user.getId()));
        stringRedisTemplate.opsForValue().set("TOKEN:" + user.getId(), token, 1, TimeUnit.DAYS);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userInfo", sanitize(user));
        return result;
    }

    public void register(RegisterDTO registerDTO) {
        if (!StringUtils.hasText(registerDTO.getUsername()) || !StringUtils.hasText(registerDTO.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, registerDTO.getUsername()).last("limit 1");
        if (sysUserMapper.selectOne(wrapper) != null) {
            throw new BusinessException("用户名已存在");
        }
        SysUser user = new SysUser();
        user.setUsername(registerDTO.getUsername().trim());
        user.setPassword(registerDTO.getPassword().trim());
        user.setRealName(StringUtils.hasText(registerDTO.getRealName()) ? registerDTO.getRealName().trim() : registerDTO.getUsername().trim());
        user.setAvatar("https://api.dicebear.com/7.x/initials/svg?seed=" + registerDTO.getUsername().trim());
        user.setRole("user");
        user.setBio("这个用户还没有留下简介");
        user.setStatus(1);
        sysUserMapper.insert(user);
    }

    public SysUser info(Long userId) {
        return sanitize(getById(userId));
    }

    public SysUser getById(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }
        return user;
    }

    public void assertAdmin(Long userId) {
        SysUser user = getById(userId);
        if (!"admin".equals(user.getRole())) {
            throw new BusinessException(403, "无权限操作");
        }
    }

    public boolean isAdmin(Long userId) {
        return "admin".equals(getById(userId).getRole());
    }

    public Long resolveUserId(String token) {
        if (!StringUtils.hasText(token) || !jwtUtils.validateToken(token)) {
            return null;
        }
        String userId = jwtUtils.getUserIdFromToken(token);
        String cacheToken = stringRedisTemplate.opsForValue().get("TOKEN:" + userId);
        if (cacheToken == null || !cacheToken.equals(token)) {
            return null;
        }
        return Long.valueOf(userId);
    }

    public void logout(Long userId) {
        stringRedisTemplate.delete("TOKEN:" + userId);
    }

    private SysUser sanitize(SysUser user) {
        if (user == null) {
            return null;
        }
        user.setPassword(null);
        return user;
    }
}
