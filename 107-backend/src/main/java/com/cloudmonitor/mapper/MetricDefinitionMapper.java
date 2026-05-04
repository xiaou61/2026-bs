package com.cloudmonitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.MetricDefinition;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MetricDefinitionMapper extends BaseMapper<MetricDefinition> {
}
