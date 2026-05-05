package com.droneinspect.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.FlightRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FlightRecordMapper extends BaseMapper<FlightRecord> {
}
