package com.twinpark.mapper;

import com.twinpark.entity.DefectReport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DefectReportMapper {
    @Select({
        "<script>",
        "SELECT * FROM defect_report",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (defect_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR severity_level LIKE CONCAT('%',#{keyword},'%') OR reporter_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DefectReport> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM defect_report WHERE id = #{id}")
    DefectReport selectById(Long id);

    @Insert("INSERT INTO defect_report (defect_no, device_name, severity_level, defect_desc, reporter_name, suggestion, status, created_time, updated_time) VALUES (#{defectNo}, #{deviceName}, #{severityLevel}, #{defectDesc}, #{reporterName}, #{suggestion}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DefectReport entity);

    @Update("UPDATE defect_report SET defect_no = #{defectNo}, device_name = #{deviceName}, severity_level = #{severityLevel}, defect_desc = #{defectDesc}, reporter_name = #{reporterName}, suggestion = #{suggestion}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DefectReport entity);

    @Delete("DELETE FROM defect_report WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM defect_report")
    long countAll();

    @Update("UPDATE defect_report SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
