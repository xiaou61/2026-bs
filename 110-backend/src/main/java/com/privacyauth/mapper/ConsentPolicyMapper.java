package com.privacyauth.mapper;

import com.privacyauth.entity.ConsentPolicy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ConsentPolicyMapper {
    @Select({
        "<script>",
        "SELECT * FROM consent_policy",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (policy_name LIKE CONCAT('%',#{keyword},'%') OR policy_code LIKE CONCAT('%',#{keyword},'%') OR purpose_name LIKE CONCAT('%',#{keyword},'%') OR policy_version LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ConsentPolicy> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM consent_policy WHERE id = #{id}")
    ConsentPolicy selectById(Long id);

    @Insert("INSERT INTO consent_policy (policy_name, policy_code, purpose_name, data_scope, policy_version, status, created_time, updated_time) VALUES (#{policyName}, #{policyCode}, #{purposeName}, #{dataScope}, #{policyVersion}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ConsentPolicy entity);

    @Update("UPDATE consent_policy SET policy_name = #{policyName}, policy_code = #{policyCode}, purpose_name = #{purposeName}, data_scope = #{dataScope}, policy_version = #{policyVersion}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ConsentPolicy entity);

    @Delete("DELETE FROM consent_policy WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM consent_policy")
    long countAll();

    @Update("UPDATE consent_policy SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
