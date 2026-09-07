package com.promptops.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.promptops.entity.OperationLog;
import com.promptops.entity.SysUser;
import com.promptops.mapper.OperationLogMapper;
import com.promptops.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OperationLogService extends ServiceImpl<OperationLogMapper, OperationLog> {

    @Autowired
    private SysUserMapper sysUserMapper;

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
        save(log);
    }
}
