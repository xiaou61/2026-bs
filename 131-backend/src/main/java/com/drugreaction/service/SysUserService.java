package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.SysUser;
import com.drugreaction.mapper.SysUserMapper;
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
        return new String[]{"username", "nickname", "role", "department"};
    }
}
