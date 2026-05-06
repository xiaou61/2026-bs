package com.econtract.mapper;

import com.econtract.entity.ContractNotice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ContractNoticeMapper {
    @Select({
        "<script>",
        "SELECT * FROM risk_warning",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (warning_no LIKE CONCAT('%',#{keyword},'%') OR project_no LIKE CONCAT('%',#{keyword},'%') OR risk_level LIKE CONCAT('%',#{keyword},'%') OR trigger_reason LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ContractNotice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM risk_warning WHERE id = #{id}")
    ContractNotice selectById(Long id);

    @Insert("INSERT INTO risk_warning (warning_no, project_no, risk_level, trigger_reason, handler_name, status, created_time, updated_time) VALUES (#{warningNo}, #{projectNo}, #{riskLevel}, #{triggerReason}, #{handlerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ContractNotice entity);

    @Update("UPDATE risk_warning SET warning_no = #{warningNo}, project_no = #{projectNo}, risk_level = #{riskLevel}, trigger_reason = #{triggerReason}, handler_name = #{handlerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ContractNotice entity);

    @Delete("DELETE FROM risk_warning WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM risk_warning")
    long countAll();

    @Update("UPDATE risk_warning SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



