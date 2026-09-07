package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.FollowUpRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FollowUpRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM follow_up_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (followup_no LIKE CONCAT('%',#{keyword},'%') OR report_no LIKE CONCAT('%',#{keyword},'%') OR followup_method LIKE CONCAT('%',#{keyword},'%') OR customer_feedback LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<FollowUpRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM follow_up_record WHERE id = #{id}")
    FollowUpRecord selectById(Long id);

    @Insert("INSERT INTO follow_up_record (followup_no, report_no, followup_method, followup_content, customer_feedback, status, created_time, updated_time) VALUES (#{followupNo}, #{reportNo}, #{followupMethod}, #{followupContent}, #{customerFeedback}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FollowUpRecord entity);

    @Update("UPDATE follow_up_record SET followup_no = #{followupNo}, report_no = #{reportNo}, followup_method = #{followupMethod}, followup_content = #{followupContent}, customer_feedback = #{customerFeedback}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(FollowUpRecord entity);

    @Delete("DELETE FROM follow_up_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM follow_up_record")
    long countAll();

    @Update("UPDATE follow_up_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
