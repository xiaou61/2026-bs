package com.twinpark.mapper;

import com.twinpark.entity.InspectionPoint;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InspectionPointMapper {
    @Select({
        "<script>",
        "SELECT * FROM inspection_point",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (point_no LIKE CONCAT('%',#{keyword},'%') OR point_name LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR check_standard LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<InspectionPoint> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM inspection_point WHERE id = #{id}")
    InspectionPoint selectById(Long id);

    @Insert("INSERT INTO inspection_point (point_no, point_name, device_name, floor_name, check_standard, last_result, status, created_time, updated_time) VALUES (#{pointNo}, #{pointName}, #{deviceName}, #{floorName}, #{checkStandard}, #{lastResult}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InspectionPoint entity);

    @Update("UPDATE inspection_point SET point_no = #{pointNo}, point_name = #{pointName}, device_name = #{deviceName}, floor_name = #{floorName}, check_standard = #{checkStandard}, last_result = #{lastResult}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(InspectionPoint entity);

    @Delete("DELETE FROM inspection_point WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM inspection_point")
    long countAll();

    @Update("UPDATE inspection_point SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
