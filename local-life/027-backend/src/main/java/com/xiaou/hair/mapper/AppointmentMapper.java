package com.xiaou.hair.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.hair.entity.Appointment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约Mapper接口
 */
@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {
}
