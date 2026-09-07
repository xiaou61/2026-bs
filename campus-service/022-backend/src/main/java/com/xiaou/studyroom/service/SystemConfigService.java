package com.xiaou.studyroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.studyroom.entity.SystemConfig;

import java.util.List;

public interface SystemConfigService extends IService<SystemConfig> {

    List<SystemConfig> listAll();

    boolean updateConfig(String configKey, String configValue, String description);
}
