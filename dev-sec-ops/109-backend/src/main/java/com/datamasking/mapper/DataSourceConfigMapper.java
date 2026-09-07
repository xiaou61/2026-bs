package com.datamasking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.DataSourceConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataSourceConfigMapper extends BaseMapper<DataSourceConfig> {
}
