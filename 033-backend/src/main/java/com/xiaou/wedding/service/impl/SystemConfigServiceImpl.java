package com.xiaou.wedding.service.impl;

import com.xiaou.wedding.entity.SystemConfig;
import com.xiaou.wedding.mapper.SystemConfigMapper;
import com.xiaou.wedding.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    public SystemConfig get(String key) {
        return systemConfigMapper.findByKey(key);
    }

    @Override
    public List<SystemConfig> list() {
        return systemConfigMapper.list();
    }

    @Override
    public void save(SystemConfig config) {
        systemConfigMapper.upsert(config);
    }
}
