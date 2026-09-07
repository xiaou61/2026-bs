package com.licensecheck.mapper;

import com.licensecheck.entity.ApprovalRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ApprovalRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM approval_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (business_type LIKE CONCAT('%',#{keyword},'%') OR applicant LIKE CONCAT('%',#{keyword},'%') OR approver LIKE CONCAT('%',#{keyword},'%') OR decision LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ApprovalRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM approval_record WHERE id = #{id}")
    ApprovalRecord selectById(Long id);

    @Insert("INSERT INTO approval_record (business_type, business_id, applicant, approver, decision, opinion, status, created_time, updated_time) VALUES (#{businessType}, #{businessId}, #{applicant}, #{approver}, #{decision}, #{opinion}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ApprovalRecord entity);

    @Update("UPDATE approval_record SET business_type = #{businessType}, business_id = #{businessId}, applicant = #{applicant}, approver = #{approver}, decision = #{decision}, opinion = #{opinion}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ApprovalRecord entity);

    @Delete("DELETE FROM approval_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM approval_record")
    long countAll();


    @Update("UPDATE approval_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
