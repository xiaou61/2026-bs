package com.eldercare.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eldercare.common.BusinessException;
import com.eldercare.entity.SysUser;
import com.eldercare.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserService {
    private final SysUserMapper mapper;

    public PageInfo<SysUser> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

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

        SysUser dbEntity = mapper.selectById(entity.getId());
        if (dbEntity == null) {
            throw new BusinessException("用户不存在");
        }
        if (entity.getPassword() == null || entity.getPassword().trim().isEmpty()) {
            entity.setPassword(dbEntity.getPassword());
        }
        if (entity.getStatus() == null) {
            entity.setStatus(dbEntity.getStatus());
        }
        mapper.update(entity);
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        mapper.updateStatus(id, status);
    }
}

