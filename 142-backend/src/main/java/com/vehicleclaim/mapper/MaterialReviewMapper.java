package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.MaterialReview;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MaterialReviewMapper {
    @Select({
        "<script>",
        "SELECT * FROM material_review",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (review_no LIKE CONCAT('%',#{keyword},'%') OR report_no LIKE CONCAT('%',#{keyword},'%') OR review_result LIKE CONCAT('%',#{keyword},'%') OR reviewer_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<MaterialReview> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM material_review WHERE id = #{id}")
    MaterialReview selectById(Long id);

    @Insert("INSERT INTO material_review (review_no, report_no, review_result, review_time, reviewer_name, status, created_time, updated_time) VALUES (#{reviewNo}, #{reportNo}, #{reviewResult}, #{reviewTime}, #{reviewerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaterialReview entity);

    @Update("UPDATE material_review SET review_no = #{reviewNo}, report_no = #{reportNo}, review_result = #{reviewResult}, review_time = #{reviewTime}, reviewer_name = #{reviewerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(MaterialReview entity);

    @Delete("DELETE FROM material_review WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM material_review")
    long countAll();

    @Update("UPDATE material_review SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
