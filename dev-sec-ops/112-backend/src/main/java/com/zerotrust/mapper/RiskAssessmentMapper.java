package com.zerotrust.mapper;

import com.zerotrust.entity.RiskAssessment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RiskAssessmentMapper {
    @Select({
        "<script>",
        "SELECT * FROM risk_assessment",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (assessment_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR employee_name LIKE CONCAT('%',#{keyword},'%') OR risk_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RiskAssessment> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM risk_assessment WHERE id = #{id}")
    RiskAssessment selectById(Long id);

    @Insert("INSERT INTO risk_assessment (assessment_no, device_name, employee_name, risk_score, risk_level, advice_text, status, created_time, updated_time) VALUES (#{assessmentNo}, #{deviceName}, #{employeeName}, #{riskScore}, #{riskLevel}, #{adviceText}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RiskAssessment entity);

    @Update("UPDATE risk_assessment SET assessment_no = #{assessmentNo}, device_name = #{deviceName}, employee_name = #{employeeName}, risk_score = #{riskScore}, risk_level = #{riskLevel}, advice_text = #{adviceText}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RiskAssessment entity);

    @Delete("DELETE FROM risk_assessment WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM risk_assessment")
    long countAll();

    @Update("UPDATE risk_assessment SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
