package com.licensecheck.mapper;

import com.licensecheck.entity.ComplianceBaseline;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ComplianceBaselineMapper {
    @Select({
        "<script>",
        "SELECT * FROM compliance_baseline",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (baseline_name LIKE CONCAT('%',#{keyword},'%') OR license_scope LIKE CONCAT('%',#{keyword},'%') OR required_level LIKE CONCAT('%',#{keyword},'%') OR effective_date LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ComplianceBaseline> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM compliance_baseline WHERE id = #{id}")
    ComplianceBaseline selectById(Long id);

    @Insert("INSERT INTO compliance_baseline (baseline_name, repository_id, license_scope, required_level, effective_date, status, created_time, updated_time) VALUES (#{baselineName}, #{repositoryId}, #{licenseScope}, #{requiredLevel}, #{effectiveDate}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ComplianceBaseline entity);

    @Update("UPDATE compliance_baseline SET baseline_name = #{baselineName}, repository_id = #{repositoryId}, license_scope = #{licenseScope}, required_level = #{requiredLevel}, effective_date = #{effectiveDate}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ComplianceBaseline entity);

    @Delete("DELETE FROM compliance_baseline WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM compliance_baseline")
    long countAll();


    @Update("UPDATE compliance_baseline SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
