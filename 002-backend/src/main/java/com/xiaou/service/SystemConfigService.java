package com.xiaou.service;

import com.xiaou.common.BusinessException;
import com.xiaou.entity.SystemConfig;
import com.xiaou.mapper.SystemConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    public SystemConfig findById(Long id) {
        SystemConfig config = systemConfigMapper.findById(id);
        if (config == null) {
            throw new BusinessException("配置不存在");
        }
        return config;
    }

    public SystemConfig findByKey(String key) {
        return systemConfigMapper.findByKey(key);
    }

    public List<SystemConfig> findAll() {
        return systemConfigMapper.findAll();
    }

    public SystemConfig update(SystemConfig config) {
        SystemConfig existConfig = systemConfigMapper.findById(config.getId());
        if (existConfig == null) {
            throw new BusinessException("配置不存在");
        }
        systemConfigMapper.update(config);
        return systemConfigMapper.findById(config.getId());
    }
}

