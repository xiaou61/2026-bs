package com.apitestcase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
