package com.devopsrelease.mapper;

import com.devopsrelease.entity.ReleasePlan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReleasePlanMapper {
    @Select({
        "<script>",
        "SELECT * FROM release_plan",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (plan_no LIKE CONCAT('%',#{keyword},'%') OR plan_title LIKE CONCAT('%',#{keyword},'%') OR release_window LIKE CONCAT('%',#{keyword},'%') OR risk_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ReleasePlan> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM release_plan WHERE id = #{id}")
    ReleasePlan selectById(Long id);

    @Insert("INSERT INTO release_plan (plan_no, plan_title, service_id, env_id, release_window, risk_level, status, description, created_time, updated_time) VALUES (#{planNo}, #{planTitle}, #{serviceId}, #{envId}, #{releaseWindow}, #{riskLevel}, #{status}, #{description}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ReleasePlan entity);

    @Update("UPDATE release_plan SET plan_no = #{planNo}, plan_title = #{planTitle}, service_id = #{serviceId}, env_id = #{envId}, release_window = #{releaseWindow}, risk_level = #{riskLevel}, status = #{status}, description = #{description}, updated_time = NOW() WHERE id = #{id}")
    int update(ReleasePlan entity);

    @Delete("DELETE FROM release_plan WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM release_plan")
    long countAll();


    @Update("UPDATE release_plan SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
