package com.xiaou.bike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.bike.entity.Station;
import org.apache.ibatis.annotations.Mapper;

/**
 * 停车点Mapper接口
 */
@Mapper
public interface StationMapper extends BaseMapper<Station> {
}
