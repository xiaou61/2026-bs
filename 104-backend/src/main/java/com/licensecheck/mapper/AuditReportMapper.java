package com.licensecheck.mapper;

import com.licensecheck.entity.AuditReport;
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
        "<if test='keyword != null and keyword != \"\"'> AND (report_no LIKE CONCAT('%',#{keyword},'%') OR report_title LIKE CONCAT('%',#{keyword},'%') OR risk_summary LIKE CONCAT('%',#{keyword},'%') OR conclusion LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AuditReport> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM audit_report WHERE id = #{id}")
    AuditReport selectById(Long id);

    @Insert("INSERT INTO audit_report (report_no, repository_id, task_id, report_title, risk_summary, conclusion, status, published_at, created_time, updated_time) VALUES (#{reportNo}, #{repositoryId}, #{taskId}, #{reportTitle}, #{riskSummary}, #{conclusion}, #{status}, #{publishedAt}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AuditReport entity);

    @Update("UPDATE audit_report SET report_no = #{reportNo}, repository_id = #{repositoryId}, task_id = #{taskId}, report_title = #{reportTitle}, risk_summary = #{riskSummary}, conclusion = #{conclusion}, status = #{status}, published_at = #{publishedAt}, updated_time = NOW() WHERE id = #{id}")
    int update(AuditReport entity);

    @Delete("DELETE FROM audit_report WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM audit_report")
    long countAll();


    @Update("UPDATE audit_report SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
