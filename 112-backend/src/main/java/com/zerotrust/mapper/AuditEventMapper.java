package com.zerotrust.mapper;

import com.zerotrust.entity.AuditEvent;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AuditEventMapper {
    @Select({
        "<script>",
        "SELECT * FROM audit_event",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (event_no LIKE CONCAT('%',#{keyword},'%') OR event_type LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR employee_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AuditEvent> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM audit_event WHERE id = #{id}")
    AuditEvent selectById(Long id);

    @Insert("INSERT INTO audit_event (event_no, event_type, device_name, employee_name, detail_text, event_time, status, created_time, updated_time) VALUES (#{eventNo}, #{eventType}, #{deviceName}, #{employeeName}, #{detailText}, #{eventTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AuditEvent entity);

    @Update("UPDATE audit_event SET event_no = #{eventNo}, event_type = #{eventType}, device_name = #{deviceName}, employee_name = #{employeeName}, detail_text = #{detailText}, event_time = #{eventTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AuditEvent entity);

    @Delete("DELETE FROM audit_event WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM audit_event")
    long countAll();

    @Update("UPDATE audit_event SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
