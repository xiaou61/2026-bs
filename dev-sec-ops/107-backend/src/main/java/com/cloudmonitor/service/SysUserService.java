package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.SysUser;
import com.cloudmonitor.mapper.SysUserMapper;
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
    protected String[] keywordColumns() {
        return new String[]{"username", "password", "nickname", "role"};
    }



@Override
public void save(SysUser entity) {
    if (entity.getPassword() == null || entity.getPassword().isEmpty()) {
        entity.setPassword("123456");
    }
    if (entity.getStatus() == null) {
        entity.setStatus(1);
    }
    super.save(entity);
}

}
