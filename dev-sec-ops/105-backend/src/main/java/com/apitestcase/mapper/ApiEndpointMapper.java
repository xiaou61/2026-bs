package com.apitestcase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.ApiEndpoint;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiEndpointMapper extends BaseMapper<ApiEndpoint> {
}
