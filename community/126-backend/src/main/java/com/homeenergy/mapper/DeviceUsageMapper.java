package com.homeenergy.mapper;

import com.homeenergy.entity.DeviceUsage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeviceUsageMapper {
    @Select({
        "<script>",
        "SELECT * FROM device_usage",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (usage_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR usage_date LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DeviceUsage> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM device_usage WHERE id = #{id}")
    DeviceUsage selectById(Long id);

    @Insert("INSERT INTO device_usage (usage_no, device_name, usage_date, energy_kwh, runtime_hour, energy_ratio, status, created_time, updated_time) VALUES (#{usageNo}, #{deviceName}, #{usageDate}, #{energyKwh}, #{runtimeHour}, #{energyRatio}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DeviceUsage entity);

    @Update("UPDATE device_usage SET usage_no = #{usageNo}, device_name = #{deviceName}, usage_date = #{usageDate}, energy_kwh = #{energyKwh}, runtime_hour = #{runtimeHour}, energy_ratio = #{energyRatio}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DeviceUsage entity);

    @Delete("DELETE FROM device_usage WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM device_usage")
    long countAll();

    @Update("UPDATE device_usage SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
