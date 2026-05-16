package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.LossAssessment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LossAssessmentMapper {
    @Select({
        "<script>",
        "SELECT * FROM loss_assessment",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (assessment_no LIKE CONCAT('%',#{keyword},'%') OR report_no LIKE CONCAT('%',#{keyword},'%') OR assessor_name LIKE CONCAT('%',#{keyword},'%') OR assessment_amount LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<LossAssessment> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM loss_assessment WHERE id = #{id}")
    LossAssessment selectById(Long id);

    @Insert("INSERT INTO loss_assessment (assessment_no, report_no, assessment_amount, assessment_time, assessor_name, status, created_time, updated_time) VALUES (#{assessmentNo}, #{reportNo}, #{assessmentAmount}, #{assessmentTime}, #{assessorName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LossAssessment entity);

    @Update("UPDATE loss_assessment SET assessment_no = #{assessmentNo}, report_no = #{reportNo}, assessment_amount = #{assessmentAmount}, assessment_time = #{assessmentTime}, assessor_name = #{assessorName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(LossAssessment entity);

    @Delete("DELETE FROM loss_assessment WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM loss_assessment")
    long countAll();

    @Update("UPDATE loss_assessment SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
