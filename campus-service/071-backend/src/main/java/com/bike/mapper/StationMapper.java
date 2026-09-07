package com.bike.mapper;

import com.bike.entity.Station;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface StationMapper {
    List<Station> findList(@Param("name") String name, @Param("status") Integer status);
    List<Station> findAll();
    Station findById(@Param("id") Long id);
    int insert(Station station);
    int update(Station station);
    int deleteById(@Param("id") Long id);
    int updateCurrentCount(@Param("id") Long id, @Param("delta") Integer delta);
    List<Map<String, Object>> stationRank();
}
