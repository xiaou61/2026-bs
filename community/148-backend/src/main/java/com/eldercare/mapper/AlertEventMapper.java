package com.eldercare.mapper;

import com.eldercare.entity.AlertEvent;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AlertEventMapper {
    @Select({
        "<script>",
        "SELECT * FROM alert_event",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (alert_no LIKE CONCAT('%',#{keyword},'%') OR elder_name LIKE CONCAT('%',#{keyword},'%') OR alert_type LIKE CONCAT('%',#{keyword},'%') OR handling_suggestion LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AlertEvent> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM alert_event WHERE id = #{id}")
    AlertEvent selectById(Long id);

    @Insert("INSERT INTO alert_event (alert_no, elder_name, report_time, alert_type, handling_suggestion, status, created_time, updated_time) VALUES (#{alertNo}, #{elderName}, #{reportTime}, #{alertType}, #{handlingSuggestion}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AlertEvent entity);

    @Update("UPDATE alert_event SET alert_no = #{alertNo}, elder_name = #{elderName}, report_time = #{reportTime}, alert_type = #{alertType}, handling_suggestion = #{handlingSuggestion}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AlertEvent entity);

    @Delete("DELETE FROM alert_event WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM alert_event")
    long countAll();

    @Update("UPDATE alert_event SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
