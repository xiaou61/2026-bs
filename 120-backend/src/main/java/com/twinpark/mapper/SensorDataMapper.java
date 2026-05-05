package com.twinpark.mapper;

import com.twinpark.entity.SensorData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SensorDataMapper {
    @Select({
        "<script>",
        "SELECT * FROM sensor_data",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (data_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR sensor_type LIKE CONCAT('%',#{keyword},'%') OR status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SensorData> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM sensor_data WHERE id = #{id}")
    SensorData selectById(Long id);

    @Insert("INSERT INTO sensor_data (data_no, device_name, sensor_type, temperature_value, energy_value, pressure_value, collect_time, status, created_time, updated_time) VALUES (#{dataNo}, #{deviceName}, #{sensorType}, #{temperatureValue}, #{energyValue}, #{pressureValue}, #{collectTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SensorData entity);

    @Update("UPDATE sensor_data SET data_no = #{dataNo}, device_name = #{deviceName}, sensor_type = #{sensorType}, temperature_value = #{temperatureValue}, energy_value = #{energyValue}, pressure_value = #{pressureValue}, collect_time = #{collectTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SensorData entity);

    @Delete("DELETE FROM sensor_data WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM sensor_data")
    long countAll();

    @Update("UPDATE sensor_data SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
