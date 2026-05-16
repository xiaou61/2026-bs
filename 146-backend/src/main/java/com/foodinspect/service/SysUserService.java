package com.foodinspect.service;

import com.foodinspect.common.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.foodinspect.entity.SysUser;
import com.foodinspect.mapper.SysUserMapper;
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
        mapper.update(entity);
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        mapper.updateStatus(id, status);
    }
}






