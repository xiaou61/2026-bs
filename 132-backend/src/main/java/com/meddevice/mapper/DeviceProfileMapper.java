package com.meddevice.mapper;

import com.meddevice.entity.DeviceProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeviceProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM device_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (device_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR model_name LIKE CONCAT('%',#{keyword},'%') OR dept_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DeviceProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM device_profile WHERE id = #{id}")
    DeviceProfile selectById(Long id);

    @Insert("INSERT INTO device_profile (device_no, device_name, model_name, dept_name, owner_name, status, created_time, updated_time) VALUES (#{deviceNo}, #{deviceName}, #{modelName}, #{deptName}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DeviceProfile entity);

    @Update("UPDATE device_profile SET device_no = #{deviceNo}, device_name = #{deviceName}, model_name = #{modelName}, dept_name = #{deptName}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DeviceProfile entity);

    @Delete("DELETE FROM device_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM device_profile")
    long countAll();

    @Update("UPDATE device_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
