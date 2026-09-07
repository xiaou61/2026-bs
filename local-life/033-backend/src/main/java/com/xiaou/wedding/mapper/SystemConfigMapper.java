package com.xiaou.wedding.mapper;

import com.xiaou.wedding.entity.SystemConfig;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SystemConfigMapper {
    SystemConfig findByKey(@Param("configKey") String configKey);

    List<SystemConfig> list();

    int upsert(SystemConfig config);
}
