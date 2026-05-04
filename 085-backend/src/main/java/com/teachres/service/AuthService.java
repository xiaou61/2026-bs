package com.teachres.service;

import com.teachres.common.BusinessException;
import com.teachres.entity.SysUser;
import com.teachres.mapper.SysUserMapper;
import com.teachres.utils.JwtUtils;
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
    private RuntimeStoreService runtimeStoreService;

    public Map<String, Object> login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BusinessException("用户名和密码不能为空");
        }
        SysUser user = sysUserMapper.selectByUsername(username);
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
        runtimeStoreService.saveToken(user.getId(), token);
        Map<String, Object> result = new HashMap<>();
        user.setPassword(null);
        result.put("token", token);
        result.put("userInfo", user);
        return result;
    }

    public SysUser info(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public void logout(Long userId) {
        runtimeStoreService.removeToken(userId);
    }
}
