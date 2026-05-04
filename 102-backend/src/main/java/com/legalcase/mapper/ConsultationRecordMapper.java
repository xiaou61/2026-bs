package com.legalcase.mapper;

import com.legalcase.entity.ConsultationRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ConsultationRecordMapper {
    @Select({"<script>",
            "SELECT * FROM consultation_record",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (question LIKE CONCAT('%',#{keyword},'%') OR answer LIKE CONCAT('%',#{keyword},'%') OR risk_level LIKE CONCAT('%',#{keyword},'%') OR follow_action LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"caseId != null\"> AND case_id = #{caseId} </if>",
            "<if test=\"clientId != null\"> AND client_id = #{clientId} </if>",
            "<if test=\"lawyerId != null\"> AND lawyer_id = #{lawyerId} </if>",
            "<if test=\"riskLevel != null and riskLevel != \'\'\"> AND risk_level = #{riskLevel} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<ConsultationRecord> selectPage(@Param("keyword") String keyword, @Param("caseId") Long caseId, @Param("clientId") Long clientId, @Param("lawyerId") Long lawyerId, @Param("riskLevel") String riskLevel);

    @Select("SELECT * FROM consultation_record WHERE id=#{id}")
    ConsultationRecord selectById(Long id);

    @Insert("INSERT INTO consultation_record(case_id, client_id, lawyer_id, consult_type, consult_time, question, answer, risk_level, follow_action, create_time, update_time) VALUES(#{caseId}, #{clientId}, #{lawyerId}, #{consultType}, #{consultTime}, #{question}, #{answer}, #{riskLevel}, #{followAction}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ConsultationRecord entity);

    @Update("UPDATE consultation_record SET case_id=#{caseId}, client_id=#{clientId}, lawyer_id=#{lawyerId}, consult_type=#{consultType}, consult_time=#{consultTime}, question=#{question}, answer=#{answer}, risk_level=#{riskLevel}, follow_action=#{followAction}, create_time=#{createTime}, update_time=#{updateTime} WHERE id=#{id}")
    int updateById(ConsultationRecord entity);

    @Delete("DELETE FROM consultation_record WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM consultation_record")
    long countAll();
}
