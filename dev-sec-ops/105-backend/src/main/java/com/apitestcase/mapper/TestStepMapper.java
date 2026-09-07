package com.apitestcase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.TestStep;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestStepMapper extends BaseMapper<TestStep> {
}
