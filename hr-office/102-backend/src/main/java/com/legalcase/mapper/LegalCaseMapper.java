package com.legalcase.mapper;

import com.legalcase.entity.LegalCase;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LegalCaseMapper {
    @Select({"<script>",
            "SELECT * FROM legal_case",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (case_no LIKE CONCAT('%',#{keyword},'%') OR title LIKE CONCAT('%',#{keyword},'%') OR case_type LIKE CONCAT('%',#{keyword},'%') OR summary LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"clientId != null\"> AND client_id = #{clientId} </if>",
            "<if test=\"lawyerId != null\"> AND lawyer_id = #{lawyerId} </if>",
            "<if test=\"caseType != null and caseType != \'\'\"> AND case_type = #{caseType} </if>",
            "<if test=\"status != null\"> AND status = #{status} </if>",
            "<if test=\"priority != null and priority != \'\'\"> AND priority = #{priority} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<LegalCase> selectPage(@Param("keyword") String keyword, @Param("clientId") Long clientId, @Param("lawyerId") Long lawyerId, @Param("caseType") String caseType, @Param("status") Integer status, @Param("priority") String priority);

    @Select("SELECT * FROM legal_case WHERE id=#{id}")
    LegalCase selectById(Long id);

    @Insert("INSERT INTO legal_case(case_no, client_id, lawyer_id, title, case_type, priority, status, summary, current_stage, next_action, create_time, update_time) VALUES(#{caseNo}, #{clientId}, #{lawyerId}, #{title}, #{caseType}, #{priority}, #{status}, #{summary}, #{currentStage}, #{nextAction}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LegalCase entity);

    @Update("UPDATE legal_case SET case_no=#{caseNo}, client_id=#{clientId}, lawyer_id=#{lawyerId}, title=#{title}, case_type=#{caseType}, priority=#{priority}, status=#{status}, summary=#{summary}, current_stage=#{currentStage}, next_action=#{nextAction}, create_time=#{createTime}, update_time=#{updateTime} WHERE id=#{id}")
    int updateById(LegalCase entity);

    @Delete("DELETE FROM legal_case WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM legal_case")
    long countAll();
}
