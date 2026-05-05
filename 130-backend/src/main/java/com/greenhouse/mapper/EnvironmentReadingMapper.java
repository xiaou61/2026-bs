package com.greenhouse.mapper;

import com.greenhouse.entity.EnvironmentReading;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EnvironmentReadingMapper {
    @Select({
        "<script>",
        "SELECT * FROM environment_reading",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (reading_no LIKE CONCAT('%',#{keyword},'%') OR greenhouse_no LIKE CONCAT('%',#{keyword},'%') OR collect_time LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<EnvironmentReading> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM environment_reading WHERE id = #{id}")
    EnvironmentReading selectById(Long id);

    @Insert("INSERT INTO environment_reading (reading_no, greenhouse_no, collect_time, temperature_value, humidity_value, status, created_time, updated_time) VALUES (#{readingNo}, #{greenhouseNo}, #{collectTime}, #{temperatureValue}, #{humidityValue}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EnvironmentReading entity);

    @Update("UPDATE environment_reading SET reading_no = #{readingNo}, greenhouse_no = #{greenhouseNo}, collect_time = #{collectTime}, temperature_value = #{temperatureValue}, humidity_value = #{humidityValue}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(EnvironmentReading entity);

    @Delete("DELETE FROM environment_reading WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM environment_reading")
    long countAll();

    @Update("UPDATE environment_reading SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
