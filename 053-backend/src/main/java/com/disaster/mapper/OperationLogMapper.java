package com.disaster.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.disaster.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
