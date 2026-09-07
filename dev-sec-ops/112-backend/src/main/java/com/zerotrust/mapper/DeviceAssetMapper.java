package com.zerotrust.mapper;

import com.zerotrust.entity.DeviceAsset;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeviceAssetMapper {
    @Select({
        "<script>",
        "SELECT * FROM device_asset",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (device_name LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%') OR device_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DeviceAsset> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM device_asset WHERE id = #{id}")
    DeviceAsset selectById(Long id);

    @Insert("INSERT INTO device_asset (device_name, device_no, device_type, os_version, owner_name, department_name, status, created_time, updated_time) VALUES (#{deviceName}, #{deviceNo}, #{deviceType}, #{osVersion}, #{ownerName}, #{departmentName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DeviceAsset entity);

    @Update("UPDATE device_asset SET device_name = #{deviceName}, device_no = #{deviceNo}, device_type = #{deviceType}, os_version = #{osVersion}, owner_name = #{ownerName}, department_name = #{departmentName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DeviceAsset entity);

    @Delete("DELETE FROM device_asset WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM device_asset")
    long countAll();

    @Update("UPDATE device_asset SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
