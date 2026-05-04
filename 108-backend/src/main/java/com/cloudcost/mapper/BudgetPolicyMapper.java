package com.cloudcost.mapper;

import com.cloudcost.entity.BudgetPolicy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BudgetPolicyMapper {
    @Select({
        "<script>",
        "SELECT * FROM budget_policy",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (policy_name LIKE CONCAT('%',#{keyword},'%') OR namespace_name LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%') OR cycle_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<BudgetPolicy> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM budget_policy WHERE id = #{id}")
    BudgetPolicy selectById(Long id);

    @Insert("INSERT INTO budget_policy (policy_name, namespace_name, budget_amount, alert_percent, owner_name, cycle_type, status, created_time, updated_time) VALUES (#{policyName}, #{namespaceName}, #{budgetAmount}, #{alertPercent}, #{ownerName}, #{cycleType}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BudgetPolicy entity);

    @Update("UPDATE budget_policy SET policy_name = #{policyName}, namespace_name = #{namespaceName}, budget_amount = #{budgetAmount}, alert_percent = #{alertPercent}, owner_name = #{ownerName}, cycle_type = #{cycleType}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(BudgetPolicy entity);

    @Delete("DELETE FROM budget_policy WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM budget_policy")
    long countAll();

    @Update("UPDATE budget_policy SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
