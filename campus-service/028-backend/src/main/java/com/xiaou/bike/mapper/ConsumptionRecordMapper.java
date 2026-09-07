package com.xiaou.bike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.bike.entity.ConsumptionRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消费记录Mapper接口
 */
@Mapper
public interface ConsumptionRecordMapper extends BaseMapper<ConsumptionRecord> {
}
