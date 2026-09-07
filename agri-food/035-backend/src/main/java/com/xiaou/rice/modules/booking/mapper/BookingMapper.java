package com.xiaou.rice.modules.booking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.rice.modules.booking.entity.Booking;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookingMapper extends BaseMapper<Booking> {
}
