package com.outpatientexam.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.outpatientexam.common.BusinessException;
import com.outpatientexam.entity.SysUser;
import com.outpatientexam.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class SysUserService {
    private final SysUserMapper mapper;

    public PageInfo<SysUser> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(SysUser entity) {
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        if (entity.getId() == null) {
            if (!StringUtils.hasText(entity.getPassword())) {
                entity.setPassword("123456");
            }
            mapper.insert(entity);
            return;
        }
        SysUser current = mapper.selectById(entity.getId());
        if (current == null) {
            throw new BusinessException("账号不存在");
        }
        if (!StringUtils.hasText(entity.getPassword())) {
            entity.setPassword(current.getPassword());
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








