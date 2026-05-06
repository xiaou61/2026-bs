package com.foodinspect.mapper;

import com.foodinspect.entity.SamplingTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SamplingTaskMapper {
    @Select({
        "<script>",
        "SELECT * FROM expense_claim",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (claim_no LIKE CONCAT('%',#{keyword},'%') OR project_no LIKE CONCAT('%',#{keyword},'%') OR applicant_name LIKE CONCAT('%',#{keyword},'%') OR claim_amount LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SamplingTask> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM expense_claim WHERE id = #{id}")
    SamplingTask selectById(Long id);

    @Insert("INSERT INTO expense_claim (claim_no, project_no, applicant_name, claim_amount, claim_time, status, created_time, updated_time) VALUES (#{claimNo}, #{projectNo}, #{applicantName}, #{claimAmount}, #{claimTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SamplingTask entity);

    @Update("UPDATE expense_claim SET claim_no = #{claimNo}, project_no = #{projectNo}, applicant_name = #{applicantName}, claim_amount = #{claimAmount}, claim_time = #{claimTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SamplingTask entity);

    @Delete("DELETE FROM expense_claim WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM expense_claim")
    long countAll();

    @Update("UPDATE expense_claim SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






