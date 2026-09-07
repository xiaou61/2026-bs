package com.smartwarehouse.mapper;

import com.smartwarehouse.entity.OutboundOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OutboundOrderMapper {
    @Select({
        "<script>",
        "SELECT * FROM outbound_order",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (outbound_no LIKE CONCAT('%',#{keyword},'%') OR customer_name LIKE CONCAT('%',#{keyword},'%') OR item_name LIKE CONCAT('%',#{keyword},'%') OR pick_location LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<OutboundOrder> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM outbound_order WHERE id = #{id}")
    OutboundOrder selectById(Long id);

    @Insert("INSERT INTO outbound_order (outbound_no, customer_name, item_name, pick_location, priority_level, quantity, status, created_time, updated_time) VALUES (#{outboundNo}, #{customerName}, #{itemName}, #{pickLocation}, #{priorityLevel}, #{quantity}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OutboundOrder entity);

    @Update("UPDATE outbound_order SET outbound_no = #{outboundNo}, customer_name = #{customerName}, item_name = #{itemName}, pick_location = #{pickLocation}, priority_level = #{priorityLevel}, quantity = #{quantity}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(OutboundOrder entity);

    @Delete("DELETE FROM outbound_order WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM outbound_order")
    long countAll();

    @Update("UPDATE outbound_order SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
