package com.datamasking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.SensitiveRule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SensitiveRuleMapper extends BaseMapper<SensitiveRule> {
}
