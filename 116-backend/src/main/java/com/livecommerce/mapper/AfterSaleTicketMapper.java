package com.livecommerce.mapper;

import com.livecommerce.entity.AfterSaleTicket;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AfterSaleTicketMapper {
    @Select({
        "<script>",
        "SELECT * FROM after_sale_ticket",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (ticket_no LIKE CONCAT('%',#{keyword},'%') OR order_no LIKE CONCAT('%',#{keyword},'%') OR issue_type LIKE CONCAT('%',#{keyword},'%') OR customer_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<AfterSaleTicket> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM after_sale_ticket WHERE id = #{id}")
    AfterSaleTicket selectById(Long id);

    @Insert("INSERT INTO after_sale_ticket (ticket_no, order_no, customer_name, issue_type, service_user, solution_text, status, created_time, updated_time) VALUES (#{ticketNo}, #{orderNo}, #{customerName}, #{issueType}, #{serviceUser}, #{solutionText}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AfterSaleTicket entity);

    @Update("UPDATE after_sale_ticket SET ticket_no = #{ticketNo}, order_no = #{orderNo}, customer_name = #{customerName}, issue_type = #{issueType}, service_user = #{serviceUser}, solution_text = #{solutionText}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(AfterSaleTicket entity);

    @Delete("DELETE FROM after_sale_ticket WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM after_sale_ticket")
    long countAll();

    @Update("UPDATE after_sale_ticket SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
