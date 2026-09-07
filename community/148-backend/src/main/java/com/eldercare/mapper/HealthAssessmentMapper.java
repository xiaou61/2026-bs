package com.eldercare.mapper;

import com.eldercare.entity.HealthAssessment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface HealthAssessmentMapper {
    @Select({
        "<script>",
        "SELECT * FROM health_assessment",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (assessment_no LIKE CONCAT('%',#{keyword},'%') OR elder_name LIKE CONCAT('%',#{keyword},'%') OR assessment_item LIKE CONCAT('%',#{keyword},'%') OR assessor_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<HealthAssessment> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM health_assessment WHERE id = #{id}")
    HealthAssessment selectById(Long id);

    @Insert("INSERT INTO health_assessment (assessment_no, elder_name, assessment_item, assessment_time, assessor_name, status, created_time, updated_time) VALUES (#{assessmentNo}, #{elderName}, #{assessmentItem}, #{assessmentTime}, #{assessorName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(HealthAssessment entity);

    @Update("UPDATE health_assessment SET assessment_no = #{assessmentNo}, elder_name = #{elderName}, assessment_item = #{assessmentItem}, assessment_time = #{assessmentTime}, assessor_name = #{assessorName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(HealthAssessment entity);

    @Delete("DELETE FROM health_assessment WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM health_assessment")
    long countAll();

    @Update("UPDATE health_assessment SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
