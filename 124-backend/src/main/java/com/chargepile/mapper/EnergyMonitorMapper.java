package com.chargepile.mapper;

import com.chargepile.entity.EnergyMonitor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EnergyMonitorMapper {
    @Select({
        "<script>",
        "SELECT * FROM energy_monitor",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (monitor_no LIKE CONCAT('%',#{keyword},'%') OR station_name LIKE CONCAT('%',#{keyword},'%') OR collect_time LIKE CONCAT('%',#{keyword},'%') OR abnormal_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<EnergyMonitor> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM energy_monitor WHERE id = #{id}")
    EnergyMonitor selectById(Long id);

    @Insert("INSERT INTO energy_monitor (monitor_no, station_name, collect_time, power_kwh, load_rate, abnormal_type, status, created_time, updated_time) VALUES (#{monitorNo}, #{stationName}, #{collectTime}, #{powerKwh}, #{loadRate}, #{abnormalType}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EnergyMonitor entity);

    @Update("UPDATE energy_monitor SET monitor_no = #{monitorNo}, station_name = #{stationName}, collect_time = #{collectTime}, power_kwh = #{powerKwh}, load_rate = #{loadRate}, abnormal_type = #{abnormalType}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(EnergyMonitor entity);

    @Delete("DELETE FROM energy_monitor WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM energy_monitor")
    long countAll();

    @Update("UPDATE energy_monitor SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
