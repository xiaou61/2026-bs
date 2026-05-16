package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.common.BusinessException;
import com.timebank.entity.SysUser;
import com.timebank.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserService extends BaseCrudService<SysUser> {
    private final SysUserMapper mapper;

    @Override
    protected BaseMapper<SysUser> mapper() {
        return mapper;
    }

    @Override
    public void save(SysUser entity) {
        if (entity.getId() == null) {
            if (entity.getPassword() == null || entity.getPassword().trim().isEmpty()) {
                entity.setPassword("123456");
            }
            if (entity.getStatus() == null) {
                entity.setStatus(1);
            }
            mapper.insert(entity);
            return;
        }
        SysUser current = mapper.selectById(entity.getId());
        if (current == null) {
            throw new BusinessException("用户不存在");
        }
        if (entity.getPassword() == null || entity.getPassword().trim().isEmpty()) {
            entity.setPassword(current.getPassword());
        }
        if (entity.getStatus() == null) {
            entity.setStatus(current.getStatus());
        }
        mapper.updateById(entity);
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"username", "nickname", "role", "department"};
    }
}

