package com.privacyauth.mapper;

import com.privacyauth.entity.RiskWarning;
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
        "<if test='keyword != null and keyword != \"\"'> AND (warning_no LIKE CONCAT('%',#{keyword},'%') OR warning_type LIKE CONCAT('%',#{keyword},'%') OR subject_name LIKE CONCAT('%',#{keyword},'%') OR risk_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RiskWarning> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM risk_warning WHERE id = #{id}")
    RiskWarning selectById(Long id);

    @Insert("INSERT INTO risk_warning (warning_no, warning_type, subject_name, risk_level, owner_name, detected_time, status, created_time, updated_time) VALUES (#{warningNo}, #{warningType}, #{subjectName}, #{riskLevel}, #{ownerName}, #{detectedTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RiskWarning entity);

    @Update("UPDATE risk_warning SET warning_no = #{warningNo}, warning_type = #{warningType}, subject_name = #{subjectName}, risk_level = #{riskLevel}, owner_name = #{ownerName}, detected_time = #{detectedTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RiskWarning entity);

    @Delete("DELETE FROM risk_warning WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM risk_warning")
    long countAll();

    @Update("UPDATE risk_warning SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
