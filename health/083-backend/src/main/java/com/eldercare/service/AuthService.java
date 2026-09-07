package com.eldercare.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eldercare.common.BusinessException;
import com.eldercare.dto.LoginDTO;
import com.eldercare.entity.SysUser;
import com.eldercare.mapper.SysUserMapper;
import com.eldercare.utils.JwtUtils;
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

    public Map<String, Object> login(LoginDTO loginDTO) {
        if (!StringUtils.hasText(loginDTO.getUsername()) || !StringUtils.hasText(loginDTO.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", loginDTO.getUsername()).eq("password", loginDTO.getPassword());
        SysUser user = sysUserMapper.selectOne(wrapper);
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException("账号已禁用");
        }
        String token = jwtUtils.generateToken(String.valueOf(user.getId()));
        runtimeStoreService.storeUserToken(user.getId(), token);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userInfo", user);
        return map;
    }

    public SysUser info(Long userId) {
        return sysUserMapper.selectById(userId);
    }

    public void logout(Long userId) {
        String token = runtimeStoreService.removeUserToken(userId);
        runtimeStoreService.invalidateToken(token);
    }
}
