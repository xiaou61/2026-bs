package com.coldchain.mapper;

import com.coldchain.entity.TemperatureRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TemperatureRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM temperature_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (record_no LIKE CONCAT('%',#{keyword},'%') OR order_no LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR cargo_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<TemperatureRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM temperature_record WHERE id = #{id}")
    TemperatureRecord selectById(Long id);

    @Insert("INSERT INTO temperature_record (record_no, order_no, device_no, cargo_name, temperature_value, humidity_value, collect_time, status, created_time, updated_time) VALUES (#{recordNo}, #{orderNo}, #{deviceNo}, #{cargoName}, #{temperatureValue}, #{humidityValue}, #{collectTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TemperatureRecord entity);

    @Update("UPDATE temperature_record SET record_no = #{recordNo}, order_no = #{orderNo}, device_no = #{deviceNo}, cargo_name = #{cargoName}, temperature_value = #{temperatureValue}, humidity_value = #{humidityValue}, collect_time = #{collectTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(TemperatureRecord entity);

    @Delete("DELETE FROM temperature_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM temperature_record")
    long countAll();

    @Update("UPDATE temperature_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
