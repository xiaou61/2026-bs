package com.chargepile.mapper;

import com.chargepile.entity.ChargingStation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChargingStationMapper {
    @Select({
        "<script>",
        "SELECT * FROM charging_station",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (station_no LIKE CONCAT('%',#{keyword},'%') OR station_name LIKE CONCAT('%',#{keyword},'%') OR city_name LIKE CONCAT('%',#{keyword},'%') OR address_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ChargingStation> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM charging_station WHERE id = #{id}")
    ChargingStation selectById(Long id);

    @Insert("INSERT INTO charging_station (station_no, station_name, city_name, address_name, operator_name, open_time, status, created_time, updated_time) VALUES (#{stationNo}, #{stationName}, #{cityName}, #{addressName}, #{operatorName}, #{openTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ChargingStation entity);

    @Update("UPDATE charging_station SET station_no = #{stationNo}, station_name = #{stationName}, city_name = #{cityName}, address_name = #{addressName}, operator_name = #{operatorName}, open_time = #{openTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ChargingStation entity);

    @Delete("DELETE FROM charging_station WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM charging_station")
    long countAll();

    @Update("UPDATE charging_station SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
