package com.kindergarten.service;

import com.kindergarten.common.BusinessException;
import com.kindergarten.entity.SysUser;
import com.kindergarten.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private AuthService authService;

    public List<SysUser> options(String targetRole, String currentRole) {
        if (!StringUtils.hasText(targetRole)) {
            throw new BusinessException("角色不能为空");
        }
        if ("parent".equals(targetRole)) {
            authService.assertTeacher(currentRole);
        } else if (!"teacher".equals(targetRole)) {
            authService.assertAdmin(currentRole);
        }
        return sysUserMapper.selectEnabledByRole(targetRole);
    }
}
