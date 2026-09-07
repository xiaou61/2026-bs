package com.xiaou.bike.service;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.bike.common.BusinessException;
import com.xiaou.bike.dto.LoginDTO;
import com.xiaou.bike.entity.Admin;
import com.xiaou.bike.mapper.AdminMapper;
import com.xiaou.bike.util.JwtUtil;
import com.xiaou.bike.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 管理员服务
 */
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;
    private final JwtUtil jwtUtil;

    /**
     * 管理员登录
     */
    public LoginVO login(LoginDTO dto) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, dto.getUsername());
        Admin admin = adminMapper.selectOne(wrapper);

        if (admin == null) {
            throw BusinessException.of("用户名或密码错误");
        }

        if (!BCrypt.checkpw(dto.getPassword(), admin.getPassword())) {
            throw BusinessException.of("用户名或密码错误");
        }

        if (admin.getStatus() == 0) {
            throw BusinessException.of("账号已被禁用");
        }

        // 生成Token
        String token = jwtUtil.generateToken(admin.getId(), admin.getUsername(), "admin");

        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setUserId(admin.getId());
        vo.setUsername(admin.getUsername());
        vo.setRealName(admin.getRealName());
        vo.setAvatar(admin.getAvatar());
        vo.setUserType("admin");
        return vo;
    }

    /**
     * 获取管理员信息
     */
    public Admin getAdminInfo(Long adminId) {
        Admin admin = adminMapper.selectById(adminId);
        if (admin != null) {
            admin.setPassword(null);
        }
        return admin;
    }
}
