package com.pethospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pethospital.entity.SysUser;
import com.pethospital.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserService {
    private final SysUserMapper mapper;

    public PageInfo<SysUser> page(Integer pageNum, Integer pageSize, String keyword, Integer status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(SysUser entity) {
        if (entity.getStatus() == null) entity.setStatus(1);
        if (entity.getId() == null) {
            if (entity.getPassword() == null || entity.getPassword().isEmpty()) entity.setPassword("123456");
            mapper.insert(entity);
        } else {
            mapper.update(entity);
        }
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public void updateStatus(Long id, Integer status) {
        mapper.updateStatus(id, status);
    }
}
