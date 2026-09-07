package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.InsurancePolicy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InsurancePolicyMapper {
    @Select({
        "<script>",
        "SELECT * FROM insurance_policy",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (policy_no LIKE CONCAT('%',#{keyword},'%') OR policy_name LIKE CONCAT('%',#{keyword},'%') OR policy_type LIKE CONCAT('%',#{keyword},'%') OR insurer_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<InsurancePolicy> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM insurance_policy WHERE id = #{id}")
    InsurancePolicy selectById(Long id);

    @Insert("INSERT INTO insurance_policy (policy_no, policy_name, policy_type, insurer_name, coverage_period, status, created_time, updated_time) VALUES (#{policyNo}, #{policyName}, #{policyType}, #{insurerName}, #{coveragePeriod}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InsurancePolicy entity);

    @Update("UPDATE insurance_policy SET policy_no = #{policyNo}, policy_name = #{policyName}, policy_type = #{policyType}, insurer_name = #{insurerName}, coverage_period = #{coveragePeriod}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(InsurancePolicy entity);

    @Delete("DELETE FROM insurance_policy WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM insurance_policy")
    long countAll();

    @Update("UPDATE insurance_policy SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
