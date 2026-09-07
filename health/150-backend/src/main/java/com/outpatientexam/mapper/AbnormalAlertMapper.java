package com.outpatientexam.mapper;

import com.outpatientexam.entity.AbnormalAlert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AbnormalAlertMapper {
    @Select({
            "<script>",
            "SELECT * FROM abnormal_alert",
            "<where>",
            "<if test='keyword != null and keyword != \"\"'> AND (alert_no LIKE CONCAT('%',#{keyword},'%') OR patient_name LIKE CONCAT('%',#{keyword},'%') OR alert_type LIKE CONCAT('%',#{keyword},'%') OR handler_name LIKE CONCAT('%',#{keyword},'%'))</if>",
            "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<AbnormalAlert> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM abnormal_alert WHERE id = #{id}")
    AbnormalAlert selectById(Long id);

    @Insert("INSERT INTO abnormal_alert (alert_no, patient_name, alert_type, alert_time, handler_name, status, created_time, updated_time) VALUES (#{alertNo}, #{patientName}, #{alertType}, #{alertTime}, #{handlerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AbnormalAlert entity);

    @Update("UPDATE abnormal_alert SET alert_no = #{alertNo}, patient_name = #{patientName}, alert_type = #{alertType}, alert_time = #{alertTime}, handler_name = #{handlerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AbnormalAlert entity);

    @Delete("DELETE FROM abnormal_alert WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM abnormal_alert")
    long countAll();

    @Update("UPDATE abnormal_alert SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
