package com.homeenergy.mapper;

import com.homeenergy.entity.EnergyReading;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EnergyReadingMapper {
    @Select({
        "<script>",
        "SELECT * FROM energy_reading",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (reading_no LIKE CONCAT('%',#{keyword},'%') OR meter_no LIKE CONCAT('%',#{keyword},'%') OR collect_time LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<EnergyReading> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM energy_reading WHERE id = #{id}")
    EnergyReading selectById(Long id);

    @Insert("INSERT INTO energy_reading (reading_no, meter_no, collect_time, total_kwh, peak_kwh, valley_kwh, status, created_time, updated_time) VALUES (#{readingNo}, #{meterNo}, #{collectTime}, #{totalKwh}, #{peakKwh}, #{valleyKwh}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EnergyReading entity);

    @Update("UPDATE energy_reading SET reading_no = #{readingNo}, meter_no = #{meterNo}, collect_time = #{collectTime}, total_kwh = #{totalKwh}, peak_kwh = #{peakKwh}, valley_kwh = #{valleyKwh}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(EnergyReading entity);

    @Delete("DELETE FROM energy_reading WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM energy_reading")
    long countAll();

    @Update("UPDATE energy_reading SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
