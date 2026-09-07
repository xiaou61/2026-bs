package com.hospital.service;

import com.hospital.entity.OperationLog;
import com.hospital.entity.SysUser;
import com.hospital.mapper.OperationLogMapper;
import com.hospital.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    public void record(Long userId, String role, String moduleName, String actionType, String description) {
        OperationLog entity = new OperationLog();
        entity.setUserId(userId);
        entity.setRole(role);
        entity.setModuleName(moduleName);
        entity.setActionType(actionType);
        entity.setDescription(description);
        entity.setCreateTime(LocalDateTime.now());
        if (userId != null) {
            SysUser user = sysUserMapper.selectById(userId);
            if (user != null) {
                entity.setUsername(user.getUsername());
            }
        }
        operationLogMapper.insert(entity);
    }
}
