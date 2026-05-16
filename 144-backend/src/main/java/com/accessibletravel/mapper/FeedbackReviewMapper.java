package com.accessibletravel.mapper;

import com.accessibletravel.entity.FeedbackReview;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FeedbackReviewMapper {
    @Select({
        "<script>",
        "SELECT * FROM feedback_review",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (feedback_no LIKE CONCAT('%',#{keyword},'%') OR volunteer_no LIKE CONCAT('%',#{keyword},'%') OR review_target LIKE CONCAT('%',#{keyword},'%') OR feedback_time LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<FeedbackReview> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM feedback_review WHERE id = #{id}")
    FeedbackReview selectById(Long id);

    @Insert("INSERT INTO feedback_review (feedback_no, volunteer_no, satisfaction_level, feedback_time, review_target, status, created_time, updated_time) VALUES (#{feedbackNo}, #{volunteerNo}, #{satisfactionLevel}, #{feedbackTime}, #{reviewTarget}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FeedbackReview entity);

    @Update("UPDATE feedback_review SET feedback_no = #{feedbackNo}, volunteer_no = #{volunteerNo}, satisfaction_level = #{satisfactionLevel}, feedback_time = #{feedbackTime}, review_target = #{reviewTarget}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(FeedbackReview entity);

    @Delete("DELETE FROM feedback_review WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM feedback_review")
    long countAll();

    @Update("UPDATE feedback_review SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

