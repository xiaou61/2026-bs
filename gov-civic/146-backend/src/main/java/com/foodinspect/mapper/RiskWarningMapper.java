package com.foodinspect.mapper;

import com.foodinspect.entity.RiskWarning;
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
        "<if test='keyword != null and keyword != \"\"'> AND (warning_no LIKE CONCAT('%',#{keyword},'%') OR food_name LIKE CONCAT('%',#{keyword},'%') OR risk_type LIKE CONCAT('%',#{keyword},'%') OR disposal_suggestion LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RiskWarning> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM risk_warning WHERE id = #{id}")
    RiskWarning selectById(Long id);

    @Insert("INSERT INTO risk_warning (warning_no, food_name, warning_time, risk_type, disposal_suggestion, status, created_time, updated_time) VALUES (#{warningNo}, #{foodName}, #{warningTime}, #{riskType}, #{disposalSuggestion}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RiskWarning entity);

    @Update("UPDATE risk_warning SET warning_no = #{warningNo}, food_name = #{foodName}, warning_time = #{warningTime}, risk_type = #{riskType}, disposal_suggestion = #{disposalSuggestion}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RiskWarning entity);

    @Delete("DELETE FROM risk_warning WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM risk_warning")
    long countAll();

    @Update("UPDATE risk_warning SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






