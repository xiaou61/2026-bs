package com.xiaou.bike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.bike.entity.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 充值记录Mapper接口
 */
@Mapper
public interface RechargeRecordMapper extends BaseMapper<RechargeRecord> {
}
