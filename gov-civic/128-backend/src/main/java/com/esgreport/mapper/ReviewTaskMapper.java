package com.esgreport.mapper;

import com.esgreport.entity.ReviewTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReviewTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM review_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (review_no LIKE CONCAT('%',#{keyword},'%') OR submission_no LIKE CONCAT('%',#{keyword},'%') OR reviewer_name LIKE CONCAT('%',#{keyword},'%') OR deadline_time LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ReviewTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM review_task WHERE id = #{id}")
    ReviewTask selectById(Long id);

    @Insert("INSERT INTO review_task (review_no, submission_no, reviewer_name, deadline_time, review_opinion, status, created_time, updated_time) VALUES (#{reviewNo}, #{submissionNo}, #{reviewerName}, #{deadlineTime}, #{reviewOpinion}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ReviewTask entity);

    @Update("UPDATE review_task SET review_no = #{reviewNo}, submission_no = #{submissionNo}, reviewer_name = #{reviewerName}, deadline_time = #{deadlineTime}, review_opinion = #{reviewOpinion}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ReviewTask entity);

    @Delete("DELETE FROM review_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM review_task")
    long countAll();

    @Update("UPDATE review_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
