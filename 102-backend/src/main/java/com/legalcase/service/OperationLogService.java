package com.legalcase.service;

import com.legalcase.entity.OperationLog;
import com.legalcase.entity.SysUser;
import com.legalcase.mapper.OperationLogMapper;
import com.legalcase.mapper.SysUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OperationLogService {
    @Autowired
    private OperationLogMapper mapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    public PageInfo<OperationLog> page(Integer pageNum, Integer pageSize, String keyword, String moduleName, String role) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, moduleName, role));
    }

    public void record(Long userId, String moduleName, String actionType, String description) {
        SysUser user = userId == null ? null : sysUserMapper.selectById(userId);
        OperationLog log = new OperationLog();
        log.setUserId(userId);
        log.setUsername(user == null ? "system" : user.getUsername());
        log.setRole(user == null ? "SYSTEM" : user.getRole());
        log.setModuleName(moduleName);
        log.setActionType(actionType);
        log.setDescription(description);
        log.setCreateTime(LocalDateTime.now());
        mapper.insert(log);
    }

    public long countAll() {
        return mapper.countAll();
    }
}
