package com.coldchain.mapper;

import com.coldchain.entity.ExceptionAlert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ExceptionAlertMapper {
    @Select({
        "<script>",
        "SELECT * FROM exception_alert",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (alert_no LIKE CONCAT('%',#{keyword},'%') OR order_no LIKE CONCAT('%',#{keyword},'%') OR alert_type LIKE CONCAT('%',#{keyword},'%') OR cargo_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ExceptionAlert> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM exception_alert WHERE id = #{id}")
    ExceptionAlert selectById(Long id);

    @Insert("INSERT INTO exception_alert (alert_no, order_no, alert_type, cargo_name, severity_level, alert_time, detail_text, status, created_time, updated_time) VALUES (#{alertNo}, #{orderNo}, #{alertType}, #{cargoName}, #{severityLevel}, #{alertTime}, #{detailText}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ExceptionAlert entity);

    @Update("UPDATE exception_alert SET alert_no = #{alertNo}, order_no = #{orderNo}, alert_type = #{alertType}, cargo_name = #{cargoName}, severity_level = #{severityLevel}, alert_time = #{alertTime}, detail_text = #{detailText}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ExceptionAlert entity);

    @Delete("DELETE FROM exception_alert WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM exception_alert")
    long countAll();

    @Update("UPDATE exception_alert SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
