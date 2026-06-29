package com.heritageworkshop.clerk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heritageworkshop.common.BusinessException;
import com.heritageworkshop.entity.SysUser;
import com.heritageworkshop.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class SysUserService {
    private final SysUserMapper mapper;

    public PageInfo<SysUser> page(Integer pageNum, Integer pageSize, String keyword, Integer status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public SysUser getById(Long id) {
        SysUser entity = mapper.selectById(id);
        if (entity == null) throw new BusinessException("用户不存在");
        return entity;
    }

    public void save(SysUser entity) {
        if (!StringUtils.hasText(entity.getUsername())) throw new BusinessException("用户名不能为空");
        if (entity.getStatus() == null) entity.setStatus(1);
        if (entity.getId() == null) {
            if (mapper.selectByUsername(entity.getUsername()) != null) throw new BusinessException("用户名已存在");
            if (!StringUtils.hasText(entity.getPassword())) entity.setPassword("123456");
            mapper.insert(entity);
        } else {
            getById(entity.getId());
            mapper.update(entity);
        }
    }

    public void delete(Long id) {
        getById(id);
        mapper.deleteById(id);
    }

    public void updateStatus(Long id, Integer status) {
        getById(id);
        mapper.updateStatus(id, status);
    }
}
