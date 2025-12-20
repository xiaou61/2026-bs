package com.xiaou.wedding.service;

import com.xiaou.wedding.entity.SystemConfig;
import java.util.List;

public interface SystemConfigService {
    SystemConfig get(String key);

    List<SystemConfig> list();

    void save(SystemConfig config);
}
