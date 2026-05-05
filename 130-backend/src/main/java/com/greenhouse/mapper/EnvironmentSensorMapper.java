package com.greenhouse.mapper;

import com.greenhouse.entity.EnvironmentSensor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EnvironmentSensorMapper {
    @Select({
        "<script>",
        "SELECT * FROM environment_sensor",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (sensor_no LIKE CONCAT('%',#{keyword},'%') OR greenhouse_no LIKE CONCAT('%',#{keyword},'%') OR sensor_type LIKE CONCAT('%',#{keyword},'%') OR install_position LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<EnvironmentSensor> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM environment_sensor WHERE id = #{id}")
    EnvironmentSensor selectById(Long id);

    @Insert("INSERT INTO environment_sensor (sensor_no, greenhouse_no, sensor_type, install_position, battery_level, status, created_time, updated_time) VALUES (#{sensorNo}, #{greenhouseNo}, #{sensorType}, #{installPosition}, #{batteryLevel}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EnvironmentSensor entity);

    @Update("UPDATE environment_sensor SET sensor_no = #{sensorNo}, greenhouse_no = #{greenhouseNo}, sensor_type = #{sensorType}, install_position = #{installPosition}, battery_level = #{batteryLevel}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(EnvironmentSensor entity);

    @Delete("DELETE FROM environment_sensor WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM environment_sensor")
    long countAll();

    @Update("UPDATE environment_sensor SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
