package com.outpatientexam.mapper;

import com.outpatientexam.entity.ExamReport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ExamReportMapper {
    @Select({
            "<script>",
            "SELECT * FROM exam_report",
            "<where>",
            "<if test='keyword != null and keyword != \"\"'> AND (report_no LIKE CONCAT('%',#{keyword},'%') OR patient_name LIKE CONCAT('%',#{keyword},'%') OR conclusion LIKE CONCAT('%',#{keyword},'%') OR technician_name LIKE CONCAT('%',#{keyword},'%'))</if>",
            "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<ExamReport> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM exam_report WHERE id = #{id}")
    ExamReport selectById(Long id);

    @Insert("INSERT INTO exam_report (report_no, patient_name, conclusion, completed_time, technician_name, status, created_time, updated_time) VALUES (#{reportNo}, #{patientName}, #{conclusion}, #{completedTime}, #{technicianName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ExamReport entity);

    @Update("UPDATE exam_report SET report_no = #{reportNo}, patient_name = #{patientName}, conclusion = #{conclusion}, completed_time = #{completedTime}, technician_name = #{technicianName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ExamReport entity);

    @Delete("DELETE FROM exam_report WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM exam_report")
    long countAll();

    @Update("UPDATE exam_report SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
