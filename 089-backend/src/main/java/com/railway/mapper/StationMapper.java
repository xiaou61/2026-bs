package com.railway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.railway.entity.Station;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StationMapper extends BaseMapper<Station> {
}
