package com.apitestcase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.MockRule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MockRuleMapper extends BaseMapper<MockRule> {
}
