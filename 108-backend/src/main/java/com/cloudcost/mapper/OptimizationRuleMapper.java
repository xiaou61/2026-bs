package com.cloudcost.mapper;

import com.cloudcost.entity.OptimizationRule;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OptimizationRuleMapper {
    @Select({
        "<script>",
        "SELECT * FROM optimization_rule",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (rule_name LIKE CONCAT('%',#{keyword},'%') OR resource_type LIKE CONCAT('%',#{keyword},'%') OR metric_name LIKE CONCAT('%',#{keyword},'%') OR action_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<OptimizationRule> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM optimization_rule WHERE id = #{id}")
    OptimizationRule selectById(Long id);

    @Insert("INSERT INTO optimization_rule (rule_name, resource_type, metric_name, threshold_value, action_type, priority, status, created_time, updated_time) VALUES (#{ruleName}, #{resourceType}, #{metricName}, #{thresholdValue}, #{actionType}, #{priority}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OptimizationRule entity);

    @Update("UPDATE optimization_rule SET rule_name = #{ruleName}, resource_type = #{resourceType}, metric_name = #{metricName}, threshold_value = #{thresholdValue}, action_type = #{actionType}, priority = #{priority}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(OptimizationRule entity);

    @Delete("DELETE FROM optimization_rule WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM optimization_rule")
    long countAll();

    @Update("UPDATE optimization_rule SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
