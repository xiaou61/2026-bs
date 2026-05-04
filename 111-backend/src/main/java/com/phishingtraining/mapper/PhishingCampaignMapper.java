package com.phishingtraining.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.PhishingCampaign;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhishingCampaignMapper extends BaseMapper<PhishingCampaign> {
}
