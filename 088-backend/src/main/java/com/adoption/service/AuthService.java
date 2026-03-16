package com.adoption.service;

import com.adoption.common.BusinessException;
import com.adoption.entity.AdopterProfile;
import com.adoption.entity.SysUser;
import com.adoption.mapper.AdopterProfileMapper;
import com.adoption.mapper.SysUserMapper;
import com.adoption.utils.JwtUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
    private AdopterProfileMapper adopterProfileMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Map<String, Object> login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BusinessException("用户名和密码不能为空");
        }
        SysUser user = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BusinessException("账号已禁用");
        }
        String token = jwtUtils.generateToken(user.getId(), user.getRole());
        stringRedisTemplate.opsForValue().set("TOKEN:" + user.getId(), token, 1, TimeUnit.DAYS);
        user.setPassword(null);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userInfo", user);
        return result;
    }

    public void register(SysUser user) {
        if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword()) || !StringUtils.hasText(user.getRealName())) {
            throw new BusinessException("注册信息不完整");
        }
        Long count = sysUserMapper.selectCount(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, user.getUsername()));
        if (count != null && count > 0) {
            throw new BusinessException("用户名已存在");
        }
        user.setRole("applicant");
        user.setStatus(1);
        sysUserMapper.insert(user);
        AdopterProfile profile = new AdopterProfile();
        profile.setUserId(user.getId());
        profile.setStatus(1);
        adopterProfileMapper.insert(profile);
    }

    public SysUser info(Long userId) {
        SysUser user = getById(userId);
        user.setPassword(null);
        return user;
    }

    public SysUser getById(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    public void logout(Long userId) {
        stringRedisTemplate.delete("TOKEN:" + userId);
    }

    public void assertAdmin(String role) {
        if (!"admin".equals(role)) {
            throw new BusinessException(403, "无权限操作");
        }
    }

    public void assertStaff(String role) {
        if (!"admin".equals(role) && !"reviewer".equals(role)) {
            throw new BusinessException(403, "无权限操作");
        }
    }
}
