package com.econtract.service;

import com.econtract.common.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.econtract.entity.SysUser;
import com.econtract.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserService {
    private final SysUserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PageInfo<SysUser> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(SysUser entity) {
        if (entity.getId() == null) {
            if (entity.getPassword() == null || entity.getPassword().trim().isEmpty()) {
                entity.setPassword("123456");
            }
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
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
        } else {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
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



