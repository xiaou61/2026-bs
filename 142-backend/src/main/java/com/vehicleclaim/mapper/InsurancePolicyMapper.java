package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.InsurancePolicy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InsurancePolicyMapper {
    @Select({
        "<script>",
        "SELECT * FROM research_project",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (project_no LIKE CONCAT('%',#{keyword},'%') OR project_name LIKE CONCAT('%',#{keyword},'%') OR leader_name LIKE CONCAT('%',#{keyword},'%') OR college_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<InsurancePolicy> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM research_project WHERE id = #{id}")
    InsurancePolicy selectById(Long id);

    @Insert("INSERT INTO research_project (project_no, project_name, leader_name, college_name, start_year, status, created_time, updated_time) VALUES (#{projectNo}, #{projectName}, #{leaderName}, #{collegeName}, #{startYear}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InsurancePolicy entity);

    @Update("UPDATE research_project SET project_no = #{projectNo}, project_name = #{projectName}, leader_name = #{leaderName}, college_name = #{collegeName}, start_year = #{startYear}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(InsurancePolicy entity);

    @Delete("DELETE FROM research_project WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM research_project")
    long countAll();

    @Update("UPDATE research_project SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}




