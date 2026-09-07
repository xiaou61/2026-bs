package com.worksite.mapper;

import com.worksite.entity.RiskWarning;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RiskWarningMapper {
    @Select({
        "<script>",
        "SELECT * FROM risk_warning",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (warning_no LIKE CONCAT('%',#{keyword},'%') OR risk_object LIKE CONCAT('%',#{keyword},'%') OR warning_level LIKE CONCAT('%',#{keyword},'%') OR warning_reason LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RiskWarning> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM risk_warning WHERE id = #{id}")
    RiskWarning selectById(Long id);

    @Insert("INSERT INTO risk_warning (warning_no, risk_object, warning_level, warning_reason, handler_name, trigger_time, status, created_time, updated_time) VALUES (#{warningNo}, #{riskObject}, #{warningLevel}, #{warningReason}, #{handlerName}, #{triggerTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RiskWarning entity);

    @Update("UPDATE risk_warning SET warning_no = #{warningNo}, risk_object = #{riskObject}, warning_level = #{warningLevel}, warning_reason = #{warningReason}, handler_name = #{handlerName}, trigger_time = #{triggerTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RiskWarning entity);

    @Delete("DELETE FROM risk_warning WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM risk_warning")
    long countAll();

    @Update("UPDATE risk_warning SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
