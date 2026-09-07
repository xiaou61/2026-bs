package com.zerotrust.mapper;

import com.zerotrust.entity.PolicyRule;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PolicyRuleMapper {
    @Select({
        "<script>",
        "SELECT * FROM policy_rule",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (rule_name LIKE CONCAT('%',#{keyword},'%') OR policy_name LIKE CONCAT('%',#{keyword},'%') OR condition_type LIKE CONCAT('%',#{keyword},'%') OR action_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<PolicyRule> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM policy_rule WHERE id = #{id}")
    PolicyRule selectById(Long id);

    @Insert("INSERT INTO policy_rule (rule_name, policy_name, condition_type, action_type, priority, status, created_time, updated_time) VALUES (#{ruleName}, #{policyName}, #{conditionType}, #{actionType}, #{priority}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PolicyRule entity);

    @Update("UPDATE policy_rule SET rule_name = #{ruleName}, policy_name = #{policyName}, condition_type = #{conditionType}, action_type = #{actionType}, priority = #{priority}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(PolicyRule entity);

    @Delete("DELETE FROM policy_rule WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM policy_rule")
    long countAll();

    @Update("UPDATE policy_rule SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
