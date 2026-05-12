package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.SysUser;
import com.legalcase.mapper.SysUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class SysUserService {
    @Autowired
    private SysUserMapper mapper;

    public PageInfo<SysUser> page(Integer pageNum, Integer pageSize, String keyword, String role, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<SysUser> info = new PageInfo<>(mapper.selectPage(keyword, role, status));
        info.getList().forEach(user -> user.setPassword(null));
        return info;
    }

    public SysUser getById(Long id) {
        SysUser user = mapper.selectById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public void saveEntity(SysUser entity) {
        if (entity.getId() == null) {
            entity.setPassword(StringUtils.hasText(entity.getPassword()) ? entity.getPassword() : "123456");
            entity.setRole(StringUtils.hasText(entity.getRole()) ? entity.getRole() : "CLIENT");
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        } else {
            SysUser db = mapper.selectById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "账号不存在");
            }
            entity.setPassword(StringUtils.hasText(entity.getPassword()) ? entity.getPassword() : db.getPassword());
            entity.setCreateTime(db.getCreateTime());
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
