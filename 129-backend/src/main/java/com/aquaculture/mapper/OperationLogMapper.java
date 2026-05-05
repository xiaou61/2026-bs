package com.aquaculture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
