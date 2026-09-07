package com.parkingguide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.PaymentRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {
}
