package com.worksite.mapper;

import com.worksite.entity.ConstructionProject;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ConstructionProjectMapper {
    @Select({
        "<script>",
        "SELECT * FROM construction_project",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (project_no LIKE CONCAT('%',#{keyword},'%') OR project_name LIKE CONCAT('%',#{keyword},'%') OR location_name LIKE CONCAT('%',#{keyword},'%') OR contractor_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ConstructionProject> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM construction_project WHERE id = #{id}")
    ConstructionProject selectById(Long id);

    @Insert("INSERT INTO construction_project (project_no, project_name, location_name, contractor_name, start_date, risk_level, status, created_time, updated_time) VALUES (#{projectNo}, #{projectName}, #{locationName}, #{contractorName}, #{startDate}, #{riskLevel}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ConstructionProject entity);

    @Update("UPDATE construction_project SET project_no = #{projectNo}, project_name = #{projectName}, location_name = #{locationName}, contractor_name = #{contractorName}, start_date = #{startDate}, risk_level = #{riskLevel}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ConstructionProject entity);

    @Delete("DELETE FROM construction_project WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM construction_project")
    long countAll();

    @Update("UPDATE construction_project SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
