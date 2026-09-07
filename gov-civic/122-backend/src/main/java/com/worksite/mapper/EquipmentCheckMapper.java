package com.worksite.mapper;

import com.worksite.entity.EquipmentCheck;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EquipmentCheckMapper {
    @Select({
        "<script>",
        "SELECT * FROM equipment_check",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (check_no LIKE CONCAT('%',#{keyword},'%') OR equipment_name LIKE CONCAT('%',#{keyword},'%') OR equipment_type LIKE CONCAT('%',#{keyword},'%') OR project_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<EquipmentCheck> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM equipment_check WHERE id = #{id}")
    EquipmentCheck selectById(Long id);

    @Insert("INSERT INTO equipment_check (check_no, equipment_name, equipment_type, project_name, check_result, inspector_name, status, created_time, updated_time) VALUES (#{checkNo}, #{equipmentName}, #{equipmentType}, #{projectName}, #{checkResult}, #{inspectorName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EquipmentCheck entity);

    @Update("UPDATE equipment_check SET check_no = #{checkNo}, equipment_name = #{equipmentName}, equipment_type = #{equipmentType}, project_name = #{projectName}, check_result = #{checkResult}, inspector_name = #{inspectorName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(EquipmentCheck entity);

    @Delete("DELETE FROM equipment_check WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM equipment_check")
    long countAll();

    @Update("UPDATE equipment_check SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
