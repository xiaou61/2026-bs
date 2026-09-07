package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.SysUser;
import com.servicequality.mapper.SysUserMapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {
    public Page<SysUser> page(Integer pageNum, Integer pageSize, String keyword, String role, Integer status) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(SysUser::getUsername, keyword).or().like(SysUser::getNickname, keyword).or().like(SysUser::getPhone, keyword));
        wrapper.eq(StringUtils.hasText(role), SysUser::getRole, role);
        wrapper.eq(status != null, SysUser::getStatus, status);
        wrapper.orderByDesc(SysUser::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(SysUser entity) {
        if (entity.getId() == null) {
            entity.setPassword(entity.getPassword() == null ? "123456" : entity.getPassword());
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

}
