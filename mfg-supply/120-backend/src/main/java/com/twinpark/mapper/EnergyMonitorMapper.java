package com.twinpark.mapper;

import com.twinpark.entity.EnergyMonitor;
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
        "<if test='keyword != null and keyword != \"\"'> AND (energy_no LIKE CONCAT('%',#{keyword},'%') OR building_name LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR energy_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<EnergyMonitor> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM energy_monitor WHERE id = #{id}")
    EnergyMonitor selectById(Long id);

    @Insert("INSERT INTO energy_monitor (energy_no, building_name, device_name, energy_type, period_name, energy_value, change_rate, status, created_time, updated_time) VALUES (#{energyNo}, #{buildingName}, #{deviceName}, #{energyType}, #{periodName}, #{energyValue}, #{changeRate}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EnergyMonitor entity);

    @Update("UPDATE energy_monitor SET energy_no = #{energyNo}, building_name = #{buildingName}, device_name = #{deviceName}, energy_type = #{energyType}, period_name = #{periodName}, energy_value = #{energyValue}, change_rate = #{changeRate}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(EnergyMonitor entity);

    @Delete("DELETE FROM energy_monitor WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM energy_monitor")
    long countAll();

    @Update("UPDATE energy_monitor SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
