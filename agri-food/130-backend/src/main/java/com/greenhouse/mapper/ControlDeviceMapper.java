package com.greenhouse.mapper;

import com.greenhouse.entity.ControlDevice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ControlDeviceMapper {
    @Select({
        "<script>",
        "SELECT * FROM control_device",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (device_no LIKE CONCAT('%',#{keyword},'%') OR greenhouse_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR device_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ControlDevice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM control_device WHERE id = #{id}")
    ControlDevice selectById(Long id);

    @Insert("INSERT INTO control_device (device_no, greenhouse_no, device_name, device_type, control_mode, status, created_time, updated_time) VALUES (#{deviceNo}, #{greenhouseNo}, #{deviceName}, #{deviceType}, #{controlMode}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ControlDevice entity);

    @Update("UPDATE control_device SET device_no = #{deviceNo}, greenhouse_no = #{greenhouseNo}, device_name = #{deviceName}, device_type = #{deviceType}, control_mode = #{controlMode}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ControlDevice entity);

    @Delete("DELETE FROM control_device WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM control_device")
    long countAll();

    @Update("UPDATE control_device SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
