package com.coldchain.mapper;

import com.coldchain.entity.ColdDevice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ColdDeviceMapper {
    @Select({
        "<script>",
        "SELECT * FROM cold_device",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (device_name LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR vehicle_no LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ColdDevice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM cold_device WHERE id = #{id}")
    ColdDevice selectById(Long id);

    @Insert("INSERT INTO cold_device (device_name, device_no, vehicle_no, owner_name, online_status, last_heartbeat, status, created_time, updated_time) VALUES (#{deviceName}, #{deviceNo}, #{vehicleNo}, #{ownerName}, #{onlineStatus}, #{lastHeartbeat}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ColdDevice entity);

    @Update("UPDATE cold_device SET device_name = #{deviceName}, device_no = #{deviceNo}, vehicle_no = #{vehicleNo}, owner_name = #{ownerName}, online_status = #{onlineStatus}, last_heartbeat = #{lastHeartbeat}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ColdDevice entity);

    @Delete("DELETE FROM cold_device WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM cold_device")
    long countAll();

    @Update("UPDATE cold_device SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
