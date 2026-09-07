package com.foodinspect.mapper;

import com.foodinspect.entity.InspectionPlan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InspectionPlanMapper {
    @Select({
        "<script>",
        "SELECT * FROM inspection_plan",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (plan_no LIKE CONCAT('%',#{keyword},'%') OR plan_name LIKE CONCAT('%',#{keyword},'%') OR inspection_type LIKE CONCAT('%',#{keyword},'%') OR region_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<InspectionPlan> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM inspection_plan WHERE id = #{id}")
    InspectionPlan selectById(Long id);

    @Insert("INSERT INTO inspection_plan (plan_no, plan_name, inspection_type, region_name, inspection_cycle, status, created_time, updated_time) VALUES (#{planNo}, #{planName}, #{inspectionType}, #{regionName}, #{inspectionCycle}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InspectionPlan entity);

    @Update("UPDATE inspection_plan SET plan_no = #{planNo}, plan_name = #{planName}, inspection_type = #{inspectionType}, region_name = #{regionName}, inspection_cycle = #{inspectionCycle}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(InspectionPlan entity);

    @Delete("DELETE FROM inspection_plan WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM inspection_plan")
    long countAll();

    @Update("UPDATE inspection_plan SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






