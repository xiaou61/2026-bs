package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.SysUser;
import com.legalcase.mapper.SysUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SysUserService {
    @Autowired
    private SysUserMapper mapper;

    public PageInfo<SysUser> page(Integer pageNum, Integer pageSize, String keyword, String role, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, role, status));
    }

    public SysUser getById(Long id) {
        return mapper.selectById(id);
    }

    public void saveEntity(SysUser entity) {
        if (entity.getId() == null) {
            entity.setPassword(entity.getPassword() == null ? "123456" : entity.getPassword());
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        if (entity.getId() == null) {
            mapper.insert(entity);
        } else {
            mapper.updateById(entity);
        }
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public long countAll() {
        return mapper.countAll();
    }
}
