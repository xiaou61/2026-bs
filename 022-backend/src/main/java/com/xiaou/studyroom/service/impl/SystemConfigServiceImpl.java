package com.xiaou.studyroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.studyroom.entity.SystemConfig;
import com.xiaou.studyroom.mapper.SystemConfigMapper;
import com.xiaou.studyroom.service.SystemConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfig> implements SystemConfigService {

    @Override
    public List<SystemConfig> listAll() {
        QueryWrapper<SystemConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("config_key");
        return list(queryWrapper);
    }

    @Override
    public boolean updateConfig(String configKey, String configValue, String description) {
        QueryWrapper<SystemConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("config_key", configKey);
        SystemConfig config = getOne(queryWrapper);
        if (config == null) {
            config = new SystemConfig();
            config.setConfigKey(configKey);
        }

        config.setConfigValue(configValue);
        config.setDescription(description);
        return saveOrUpdate(config);
    }
}
