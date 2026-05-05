package com.esgreport.mapper;

import com.esgreport.entity.CompanySubmission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CompanySubmissionMapper {
    @Select({
        "<script>",
        "SELECT * FROM company_submission",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (submission_no LIKE CONCAT('%',#{keyword},'%') OR company_name LIKE CONCAT('%',#{keyword},'%') OR period_month LIKE CONCAT('%',#{keyword},'%') OR filler_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CompanySubmission> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM company_submission WHERE id = #{id}")
    CompanySubmission selectById(Long id);

    @Insert("INSERT INTO company_submission (submission_no, company_name, period_month, filler_name, submit_time, status, created_time, updated_time) VALUES (#{submissionNo}, #{companyName}, #{periodMonth}, #{fillerName}, #{submitTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CompanySubmission entity);

    @Update("UPDATE company_submission SET submission_no = #{submissionNo}, company_name = #{companyName}, period_month = #{periodMonth}, filler_name = #{fillerName}, submit_time = #{submitTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CompanySubmission entity);

    @Delete("DELETE FROM company_submission WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM company_submission")
    long countAll();

    @Update("UPDATE company_submission SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
