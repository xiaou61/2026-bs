package com.recruitmatch.service;

import com.recruitmatch.entity.SysUser;
import com.recruitmatch.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {
    public Page<SysUser> page(Integer pageNum, Integer pageSize, String keyword, String role, Integer status) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(SysUser::getUsername, keyword).or().like(SysUser::getNickname, keyword).or().like(SysUser::getDepartment, keyword));
        wrapper.eq(StringUtils.hasText(role), SysUser::getRole, role);
        wrapper.eq(status != null, SysUser::getStatus, status);
        wrapper.orderByDesc(SysUser::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveUser(SysUser user) {
        if (user.getId() == null) {
            user.setPassword(user.getPassword() == null ? "123456" : user.getPassword());
            user.setStatus(user.getStatus() == null ? 1 : user.getStatus());
            user.setCreateTime(LocalDateTime.now());
        }
        user.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(user);
    }
}
