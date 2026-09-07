package com.apitestcase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.EnvironmentConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnvironmentConfigMapper extends BaseMapper<EnvironmentConfig> {
}
