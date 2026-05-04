package com.aigccopyright.service;

import com.aigccopyright.common.BusinessException;
import com.aigccopyright.entity.SysUser;
import com.aigccopyright.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class UserService extends ServiceImpl<SysUserMapper, SysUser> {

    public Page<SysUser> page(Integer pageNum, Integer pageSize, String keyword, String role, Integer status) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(SysUser::getUsername, keyword).or().like(SysUser::getNickname, keyword));
        wrapper.eq(StringUtils.hasText(role), SysUser::getRole, role);
        wrapper.eq(status != null, SysUser::getStatus, status);
        wrapper.orderByDesc(SysUser::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveUser(SysUser user) {
        if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getNickname()) || !StringUtils.hasText(user.getRole())) {
            throw new BusinessException(400, "用户信息不完整");
        }
        if (user.getId() == null) {
            long count = count(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, user.getUsername()));
            if (count > 0) {
                throw new BusinessException(400, "用户名已存在");
            }
            user.setPassword(StringUtils.hasText(user.getPassword()) ? user.getPassword() : "123456");
            user.setStatus(user.getStatus() == null ? 1 : user.getStatus());
            user.setCreateTime(LocalDateTime.now());
        }
        user.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(user);
    }
}
