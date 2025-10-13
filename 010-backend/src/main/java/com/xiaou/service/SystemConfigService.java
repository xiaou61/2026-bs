package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.SystemConfig;
import com.xiaou.mapper.SystemConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    public List<SystemConfig> listAll() {
        return systemConfigMapper.selectList(null);
    }

    public SystemConfig getByKey(String key) {
        LambdaQueryWrapper<SystemConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemConfig::getConfigKey, key);
        return systemConfigMapper.selectOne(wrapper);
    }

    public String getValueByKey(String key) {
        SystemConfig config = getByKey(key);
        return config != null ? config.getConfigValue() : null;
    }

    public Integer getIntValueByKey(String key, Integer defaultValue) {
        String value = getValueByKey(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    public void updateConfig(Long id, String value) {
        SystemConfig config = systemConfigMapper.selectById(id);
        config.setConfigValue(value);
        config.setUpdateTime(LocalDateTime.now());
        systemConfigMapper.updateById(config);
    }
}

