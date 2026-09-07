package com.privacyauth.mapper;

import com.privacyauth.entity.AuditReport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AuditReportMapper {
    @Select({
        "<script>",
        "SELECT * FROM audit_report",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (report_no LIKE CONCAT('%',#{keyword},'%') OR report_name LIKE CONCAT('%',#{keyword},'%') OR report_period LIKE CONCAT('%',#{keyword},'%') OR generated_by LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AuditReport> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM audit_report WHERE id = #{id}")
    AuditReport selectById(Long id);

    @Insert("INSERT INTO audit_report (report_no, report_name, report_period, generated_by, summary_text, status, created_time, updated_time) VALUES (#{reportNo}, #{reportName}, #{reportPeriod}, #{generatedBy}, #{summaryText}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AuditReport entity);

    @Update("UPDATE audit_report SET report_no = #{reportNo}, report_name = #{reportName}, report_period = #{reportPeriod}, generated_by = #{generatedBy}, summary_text = #{summaryText}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AuditReport entity);

    @Delete("DELETE FROM audit_report WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM audit_report")
    long countAll();

    @Update("UPDATE audit_report SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
