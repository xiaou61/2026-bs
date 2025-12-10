package com.xiaou.bike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.bike.entity.CreditRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 信用记录Mapper接口
 */
@Mapper
public interface CreditRecordMapper extends BaseMapper<CreditRecord> {
}
