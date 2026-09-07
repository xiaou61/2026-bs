package com.homeenergy.mapper;

import com.homeenergy.entity.MaintenanceTicket;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MaintenanceTicketMapper {
    @Select({
        "<script>",
        "SELECT * FROM maintenance_ticket",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (ticket_no LIKE CONCAT('%',#{keyword},'%') OR home_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR issue_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<MaintenanceTicket> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM maintenance_ticket WHERE id = #{id}")
    MaintenanceTicket selectById(Long id);

    @Insert("INSERT INTO maintenance_ticket (ticket_no, home_no, device_name, issue_type, maintainer_name, appointment_time, status, created_time, updated_time) VALUES (#{ticketNo}, #{homeNo}, #{deviceName}, #{issueType}, #{maintainerName}, #{appointmentTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaintenanceTicket entity);

    @Update("UPDATE maintenance_ticket SET ticket_no = #{ticketNo}, home_no = #{homeNo}, device_name = #{deviceName}, issue_type = #{issueType}, maintainer_name = #{maintainerName}, appointment_time = #{appointmentTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(MaintenanceTicket entity);

    @Delete("DELETE FROM maintenance_ticket WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM maintenance_ticket")
    long countAll();

    @Update("UPDATE maintenance_ticket SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
