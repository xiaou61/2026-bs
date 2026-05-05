package com.worksite.mapper;

import com.worksite.entity.HazardReport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface HazardReportMapper {
    @Select({
        "<script>",
        "SELECT * FROM hazard_report",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (hazard_no LIKE CONCAT('%',#{keyword},'%') OR project_name LIKE CONCAT('%',#{keyword},'%') OR hazard_type LIKE CONCAT('%',#{keyword},'%') OR severity_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<HazardReport> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM hazard_report WHERE id = #{id}")
    HazardReport selectById(Long id);

    @Insert("INSERT INTO hazard_report (hazard_no, project_name, hazard_type, severity_level, reporter_name, report_time, status, created_time, updated_time) VALUES (#{hazardNo}, #{projectName}, #{hazardType}, #{severityLevel}, #{reporterName}, #{reportTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(HazardReport entity);

    @Update("UPDATE hazard_report SET hazard_no = #{hazardNo}, project_name = #{projectName}, hazard_type = #{hazardType}, severity_level = #{severityLevel}, reporter_name = #{reporterName}, report_time = #{reportTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(HazardReport entity);

    @Delete("DELETE FROM hazard_report WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM hazard_report")
    long countAll();

    @Update("UPDATE hazard_report SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
