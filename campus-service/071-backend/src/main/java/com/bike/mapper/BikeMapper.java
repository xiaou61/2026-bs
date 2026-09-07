package com.bike.mapper;

import com.bike.entity.Bike;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface BikeMapper {
    List<Bike> findList(@Param("bikeNo") String bikeNo, @Param("type") Integer type, @Param("status") Integer status, @Param("stationId") Long stationId);
    Bike findById(@Param("id") Long id);
    List<Bike> findAvailableByStation(@Param("stationId") Long stationId);
    int insert(Bike bike);
    int update(Bike bike);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int updateStation(@Param("id") Long id, @Param("stationId") Long stationId);
    int deleteById(@Param("id") Long id);
    int countTotal();
    int countByStatus(@Param("status") Integer status);
    List<Map<String, Object>> countByType();
}
