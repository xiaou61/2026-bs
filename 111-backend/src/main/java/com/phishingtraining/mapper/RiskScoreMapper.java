package com.phishingtraining.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.RiskScore;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RiskScoreMapper extends BaseMapper<RiskScore> {
}
