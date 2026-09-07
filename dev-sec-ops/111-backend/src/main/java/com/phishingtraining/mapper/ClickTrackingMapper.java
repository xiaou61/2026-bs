package com.phishingtraining.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.ClickTracking;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClickTrackingMapper extends BaseMapper<ClickTracking> {
}
