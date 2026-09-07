package com.xiaou.mapper;

import com.xiaou.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemConfigMapper {
    SystemConfig findById(Long id);
    
    SystemConfig findByKey(String configKey);
    
    List<SystemConfig> findAll();
    
    int insert(SystemConfig config);
    
    int update(SystemConfig config);
    
    int deleteById(Long id);
}

