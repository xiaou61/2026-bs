package com.meddevice.mapper;

import com.meddevice.entity.DeviceCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeviceCategoryMapper {
    @Select({
        "<script>",
        "SELECT * FROM device_category",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (category_no LIKE CONCAT('%',#{keyword},'%') OR category_name LIKE CONCAT('%',#{keyword},'%') OR risk_level LIKE CONCAT('%',#{keyword},'%') OR sterilize_method LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DeviceCategory> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM device_category WHERE id = #{id}")
    DeviceCategory selectById(Long id);

    @Insert("INSERT INTO device_category (category_no, category_name, risk_level, sterilize_method, manager_name, status, created_time, updated_time) VALUES (#{categoryNo}, #{categoryName}, #{riskLevel}, #{sterilizeMethod}, #{managerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DeviceCategory entity);

    @Update("UPDATE device_category SET category_no = #{categoryNo}, category_name = #{categoryName}, risk_level = #{riskLevel}, sterilize_method = #{sterilizeMethod}, manager_name = #{managerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DeviceCategory entity);

    @Delete("DELETE FROM device_category WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM device_category")
    long countAll();

    @Update("UPDATE device_category SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
