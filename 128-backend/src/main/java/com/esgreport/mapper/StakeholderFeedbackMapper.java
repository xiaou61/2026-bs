package com.esgreport.mapper;

import com.esgreport.entity.StakeholderFeedback;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StakeholderFeedbackMapper {
    @Select({
        "<script>",
        "SELECT * FROM stakeholder_feedback",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (feedback_no LIKE CONCAT('%',#{keyword},'%') OR stakeholder_name LIKE CONCAT('%',#{keyword},'%') OR feedback_type LIKE CONCAT('%',#{keyword},'%') OR content_text LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<StakeholderFeedback> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM stakeholder_feedback WHERE id = #{id}")
    StakeholderFeedback selectById(Long id);

    @Insert("INSERT INTO stakeholder_feedback (feedback_no, stakeholder_name, feedback_type, content_text, handler_name, status, created_time, updated_time) VALUES (#{feedbackNo}, #{stakeholderName}, #{feedbackType}, #{contentText}, #{handlerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(StakeholderFeedback entity);

    @Update("UPDATE stakeholder_feedback SET feedback_no = #{feedbackNo}, stakeholder_name = #{stakeholderName}, feedback_type = #{feedbackType}, content_text = #{contentText}, handler_name = #{handlerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(StakeholderFeedback entity);

    @Delete("DELETE FROM stakeholder_feedback WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM stakeholder_feedback")
    long countAll();

    @Update("UPDATE stakeholder_feedback SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
