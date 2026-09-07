package com.greenhouse.mapper;

import com.greenhouse.entity.FertilizerPlan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FertilizerPlanMapper {
    @Select({
        "<script>",
        "SELECT * FROM fertilizer_plan",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (plan_no LIKE CONCAT('%',#{keyword},'%') OR greenhouse_no LIKE CONCAT('%',#{keyword},'%') OR fertilizer_type LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<FertilizerPlan> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM fertilizer_plan WHERE id = #{id}")
    FertilizerPlan selectById(Long id);

    @Insert("INSERT INTO fertilizer_plan (plan_no, greenhouse_no, fertilizer_type, plan_amount, owner_name, status, created_time, updated_time) VALUES (#{planNo}, #{greenhouseNo}, #{fertilizerType}, #{planAmount}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FertilizerPlan entity);

    @Update("UPDATE fertilizer_plan SET plan_no = #{planNo}, greenhouse_no = #{greenhouseNo}, fertilizer_type = #{fertilizerType}, plan_amount = #{planAmount}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(FertilizerPlan entity);

    @Delete("DELETE FROM fertilizer_plan WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM fertilizer_plan")
    long countAll();

    @Update("UPDATE fertilizer_plan SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
