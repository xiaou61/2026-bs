package com.textintegrity.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommonMapper {

    @SelectProvider(type = CommonSqlProvider.class, method = "selectPage")
    List<Map<String, Object>> selectPage(@Param("table") String table,
                                         @Param("columns") String columns,
                                         @Param("keyword") String keyword,
                                         @Param("keywordColumns") List<String> keywordColumns,
                                         @Param("filters") Map<String, Object> filters,
                                         @Param("orderBy") String orderBy);

    @SelectProvider(type = CommonSqlProvider.class, method = "selectById")
    Map<String, Object> selectById(@Param("table") String table, @Param("columns") String columns, @Param("id") Long id);

    @InsertProvider(type = CommonSqlProvider.class, method = "insert")
    void insert(@Param("table") String table, @Param("data") Map<String, Object> data);

    @UpdateProvider(type = CommonSqlProvider.class, method = "update")
    void update(@Param("table") String table, @Param("data") Map<String, Object> data);

    @DeleteProvider(type = CommonSqlProvider.class, method = "delete")
    void delete(@Param("table") String table, @Param("id") Long id);

    @UpdateProvider(type = CommonSqlProvider.class, method = "updateField")
    void updateField(@Param("table") String table, @Param("id") Long id, @Param("field") String field, @Param("value") Object value);

    @SelectProvider(type = CommonSqlProvider.class, method = "countAll")
    Long countAll(@Param("table") String table);

    @SelectProvider(type = CommonSqlProvider.class, method = "countWhere")
    Long countWhere(@Param("table") String table, @Param("field") String field, @Param("value") Object value);

    @Select("select id, username, password, nickname, role, department, phone, email, status, create_time createTime, update_time updateTime from sys_user where username = #{username}")
    Map<String, Object> findUserByUsername(String username);

    @Select("select id, username, password, nickname, role, department, phone, email, status, create_time createTime, update_time updateTime from sys_user where id = #{id}")
    Map<String, Object> findUserById(Long id);

    @Select("select id, assignment_id assignmentId, student_id studentId, title, content, citation_note citationNote, word_count wordCount, status from text_submission where id = #{id}")
    Map<String, Object> findSubmissionById(Long id);

    @Select("select id, rule_name ruleName, rule_type ruleType, risk_level riskLevel, weight, keywords, status from detection_rule where status = 1 order by weight desc, id asc")
    List<Map<String, Object>> findEnabledRules();

    @Select("select id, task_no taskNo, submission_id submissionId, task_name taskName, priority, status, reviewer_id reviewerId from detection_task where id = #{id}")
    Map<String, Object> findTaskById(Long id);

    @Select("select ifnull(avg(score), 0) from detection_result")
    BigDecimal averageScore();
}
