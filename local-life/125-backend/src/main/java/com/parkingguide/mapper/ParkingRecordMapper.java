package com.parkingguide.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.ParkingRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ParkingRecordMapper extends BaseMapper<ParkingRecord> {
}
