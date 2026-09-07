package com.econtract.mapper;

import com.econtract.entity.RiskClause;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RiskClauseMapper {
    @Select({
        "<script>",
        "SELECT * FROM risk_clause",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (clause_no LIKE CONCAT('%',#{keyword},'%') OR contract_title LIKE CONCAT('%',#{keyword},'%') OR clause_type LIKE CONCAT('%',#{keyword},'%') OR risk_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RiskClause> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM risk_clause WHERE id = #{id}")
    RiskClause selectById(Long id);

    @Insert("INSERT INTO risk_clause (clause_no, contract_title, clause_type, risk_level, reviewer_name, status, created_time, updated_time) VALUES (#{clauseNo}, #{contractTitle}, #{clauseType}, #{riskLevel}, #{reviewerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RiskClause entity);

    @Update("UPDATE risk_clause SET clause_no = #{clauseNo}, contract_title = #{contractTitle}, clause_type = #{clauseType}, risk_level = #{riskLevel}, reviewer_name = #{reviewerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RiskClause entity);

    @Delete("DELETE FROM risk_clause WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM risk_clause")
    long countAll();

    @Update("UPDATE risk_clause SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



