package com.greenhouse.mapper;

import com.greenhouse.entity.MaintenanceTicket;
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
        "<if test='keyword != null and keyword != \"\"'> AND (ticket_no LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR issue_type LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<MaintenanceTicket> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM maintenance_ticket WHERE id = #{id}")
    MaintenanceTicket selectById(Long id);

    @Insert("INSERT INTO maintenance_ticket (ticket_no, device_no, issue_type, owner_name, deadline_time, status, created_time, updated_time) VALUES (#{ticketNo}, #{deviceNo}, #{issueType}, #{ownerName}, #{deadlineTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaintenanceTicket entity);

    @Update("UPDATE maintenance_ticket SET ticket_no = #{ticketNo}, device_no = #{deviceNo}, issue_type = #{issueType}, owner_name = #{ownerName}, deadline_time = #{deadlineTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(MaintenanceTicket entity);

    @Delete("DELETE FROM maintenance_ticket WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM maintenance_ticket")
    long countAll();

    @Update("UPDATE maintenance_ticket SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
