package com.drugreaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
