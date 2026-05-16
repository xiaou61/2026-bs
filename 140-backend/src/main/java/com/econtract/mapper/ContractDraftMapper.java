package com.econtract.mapper;

import com.econtract.entity.ContractDraft;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ContractDraftMapper {
    @Select({
        "<script>",
        "SELECT * FROM contract_draft",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (draft_no LIKE CONCAT('%',#{keyword},'%') OR template_name LIKE CONCAT('%',#{keyword},'%') OR contract_title LIKE CONCAT('%',#{keyword},'%') OR applicant_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ContractDraft> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM contract_draft WHERE id = #{id}")
    ContractDraft selectById(Long id);

    @Insert("INSERT INTO contract_draft (draft_no, template_name, contract_title, applicant_name, submit_time, status, created_time, updated_time) VALUES (#{draftNo}, #{templateName}, #{contractTitle}, #{applicantName}, #{submitTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ContractDraft entity);

    @Update("UPDATE contract_draft SET draft_no = #{draftNo}, template_name = #{templateName}, contract_title = #{contractTitle}, applicant_name = #{applicantName}, submit_time = #{submitTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ContractDraft entity);

    @Delete("DELETE FROM contract_draft WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM contract_draft")
    long countAll();

    @Update("UPDATE contract_draft SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



