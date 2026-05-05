package com.homeenergy.mapper;

import com.homeenergy.entity.SmartMeter;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SmartMeterMapper {
    @Select({
        "<script>",
        "SELECT * FROM smart_meter",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (meter_no LIKE CONCAT('%',#{keyword},'%') OR home_no LIKE CONCAT('%',#{keyword},'%') OR install_location LIKE CONCAT('%',#{keyword},'%') OR communication_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SmartMeter> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM smart_meter WHERE id = #{id}")
    SmartMeter selectById(Long id);

    @Insert("INSERT INTO smart_meter (meter_no, home_no, install_location, communication_type, ratio_value, last_heartbeat, status, created_time, updated_time) VALUES (#{meterNo}, #{homeNo}, #{installLocation}, #{communicationType}, #{ratioValue}, #{lastHeartbeat}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SmartMeter entity);

    @Update("UPDATE smart_meter SET meter_no = #{meterNo}, home_no = #{homeNo}, install_location = #{installLocation}, communication_type = #{communicationType}, ratio_value = #{ratioValue}, last_heartbeat = #{lastHeartbeat}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SmartMeter entity);

    @Delete("DELETE FROM smart_meter WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM smart_meter")
    long countAll();

    @Update("UPDATE smart_meter SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
