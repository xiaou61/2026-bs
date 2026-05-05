package com.parkingguide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.ReservationOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationOrderMapper extends BaseMapper<ReservationOrder> {
}
