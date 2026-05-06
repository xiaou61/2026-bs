package com.foodinspect.mapper;

import com.foodinspect.entity.SampleRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SampleRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM approval_task",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (approval_no LIKE CONCAT('%',#{keyword},'%') OR claim_no LIKE CONCAT('%',#{keyword},'%') OR node_name LIKE CONCAT('%',#{keyword},'%') OR approver_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SampleRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM approval_task WHERE id = #{id}")
    SampleRecord selectById(Long id);

    @Insert("INSERT INTO approval_task (approval_no, claim_no, node_name, approver_name, approval_opinion, status, created_time, updated_time) VALUES (#{approvalNo}, #{claimNo}, #{nodeName}, #{approverName}, #{approvalOpinion}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SampleRecord entity);

    @Update("UPDATE approval_task SET approval_no = #{approvalNo}, claim_no = #{claimNo}, node_name = #{nodeName}, approver_name = #{approverName}, approval_opinion = #{approvalOpinion}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SampleRecord entity);

    @Delete("DELETE FROM approval_task WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM approval_task")
    long countAll();

    @Update("UPDATE approval_task SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






