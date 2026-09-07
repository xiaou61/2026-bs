package com.homeenergy.mapper;

import com.homeenergy.entity.ElectricDevice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ElectricDeviceMapper {
    @Select({
        "<script>",
        "SELECT * FROM electric_device",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (device_no LIKE CONCAT('%',#{keyword},'%') OR home_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR device_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ElectricDevice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM electric_device WHERE id = #{id}")
    ElectricDevice selectById(Long id);

    @Insert("INSERT INTO electric_device (device_no, home_no, device_name, device_type, rated_power, room_name, status, created_time, updated_time) VALUES (#{deviceNo}, #{homeNo}, #{deviceName}, #{deviceType}, #{ratedPower}, #{roomName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ElectricDevice entity);

    @Update("UPDATE electric_device SET device_no = #{deviceNo}, home_no = #{homeNo}, device_name = #{deviceName}, device_type = #{deviceType}, rated_power = #{ratedPower}, room_name = #{roomName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ElectricDevice entity);

    @Delete("DELETE FROM electric_device WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM electric_device")
    long countAll();

    @Update("UPDATE electric_device SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
