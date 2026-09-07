package com.apitestcase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.ApiProject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiProjectMapper extends BaseMapper<ApiProject> {
}
