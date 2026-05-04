package com.coldchain.mapper;

import com.coldchain.entity.AlertRule;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AlertRuleMapper {
    @Select({
        "<script>",
        "SELECT * FROM alert_rule",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (rule_name LIKE CONCAT('%',#{keyword},'%') OR rule_code LIKE CONCAT('%',#{keyword},'%') OR rule_type LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AlertRule> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM alert_rule WHERE id = #{id}")
    AlertRule selectById(Long id);

    @Insert("INSERT INTO alert_rule (rule_name, rule_code, rule_type, threshold_value, owner_name, status, created_time, updated_time) VALUES (#{ruleName}, #{ruleCode}, #{ruleType}, #{thresholdValue}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AlertRule entity);

    @Update("UPDATE alert_rule SET rule_name = #{ruleName}, rule_code = #{ruleCode}, rule_type = #{ruleType}, threshold_value = #{thresholdValue}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AlertRule entity);

    @Delete("DELETE FROM alert_rule WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM alert_rule")
    long countAll();

    @Update("UPDATE alert_rule SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
