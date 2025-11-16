package com.xiaou.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.studyroom.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {
}