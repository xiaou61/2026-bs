package com.licensecheck.mapper;

import com.licensecheck.entity.LicensePolicy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LicensePolicyMapper {
    @Select({
        "<script>",
        "SELECT * FROM license_policy",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (license_name LIKE CONCAT('%',#{keyword},'%') OR risk_level LIKE CONCAT('%',#{keyword},'%') OR policy_text LIKE CONCAT('%',#{keyword},'%') OR status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<LicensePolicy> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM license_policy WHERE id = #{id}")
    LicensePolicy selectById(Long id);

    @Insert("INSERT INTO license_policy (license_name, risk_level, compatible_flag, commercial_use_flag, policy_text, status, created_time, updated_time) VALUES (#{licenseName}, #{riskLevel}, #{compatibleFlag}, #{commercialUseFlag}, #{policyText}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LicensePolicy entity);

    @Update("UPDATE license_policy SET license_name = #{licenseName}, risk_level = #{riskLevel}, compatible_flag = #{compatibleFlag}, commercial_use_flag = #{commercialUseFlag}, policy_text = #{policyText}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(LicensePolicy entity);

    @Delete("DELETE FROM license_policy WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM license_policy")
    long countAll();


    @Update("UPDATE license_policy SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
