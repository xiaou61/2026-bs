package com.smartwarehouse.mapper;

import com.smartwarehouse.entity.InboundOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InboundOrderMapper {
    @Select({
        "<script>",
        "SELECT * FROM inbound_order",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (inbound_no LIKE CONCAT('%',#{keyword},'%') OR supplier_name LIKE CONCAT('%',#{keyword},'%') OR item_name LIKE CONCAT('%',#{keyword},'%') OR target_location LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<InboundOrder> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM inbound_order WHERE id = #{id}")
    InboundOrder selectById(Long id);

    @Insert("INSERT INTO inbound_order (inbound_no, supplier_name, item_name, target_location, plan_time, quantity, status, created_time, updated_time) VALUES (#{inboundNo}, #{supplierName}, #{itemName}, #{targetLocation}, #{planTime}, #{quantity}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InboundOrder entity);

    @Update("UPDATE inbound_order SET inbound_no = #{inboundNo}, supplier_name = #{supplierName}, item_name = #{itemName}, target_location = #{targetLocation}, plan_time = #{planTime}, quantity = #{quantity}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(InboundOrder entity);

    @Delete("DELETE FROM inbound_order WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM inbound_order")
    long countAll();

    @Update("UPDATE inbound_order SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
