package com.meddevice.mapper;

import com.meddevice.entity.RiskAlert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RiskAlertMapper {
    @Select({
        "<script>",
        "SELECT * FROM risk_alert",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (alert_no LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR alert_level LIKE CONCAT('%',#{keyword},'%') OR trigger_reason LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RiskAlert> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM risk_alert WHERE id = #{id}")
    RiskAlert selectById(Long id);

    @Insert("INSERT INTO risk_alert (alert_no, device_no, alert_level, trigger_reason, handler_name, status, created_time, updated_time) VALUES (#{alertNo}, #{deviceNo}, #{alertLevel}, #{triggerReason}, #{handlerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RiskAlert entity);

    @Update("UPDATE risk_alert SET alert_no = #{alertNo}, device_no = #{deviceNo}, alert_level = #{alertLevel}, trigger_reason = #{triggerReason}, handler_name = #{handlerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RiskAlert entity);

    @Delete("DELETE FROM risk_alert WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM risk_alert")
    long countAll();

    @Update("UPDATE risk_alert SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
