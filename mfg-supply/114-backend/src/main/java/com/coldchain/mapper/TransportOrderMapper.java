package com.coldchain.mapper;

import com.coldchain.entity.TransportOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TransportOrderMapper {
    @Select({
        "<script>",
        "SELECT * FROM transport_order",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (order_no LIKE CONCAT('%',#{keyword},'%') OR cargo_name LIKE CONCAT('%',#{keyword},'%') OR from_node LIKE CONCAT('%',#{keyword},'%') OR to_node LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<TransportOrder> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM transport_order WHERE id = #{id}")
    TransportOrder selectById(Long id);

    @Insert("INSERT INTO transport_order (order_no, cargo_name, from_node, to_node, carrier_name, vehicle_no, status, created_time, updated_time) VALUES (#{orderNo}, #{cargoName}, #{fromNode}, #{toNode}, #{carrierName}, #{vehicleNo}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TransportOrder entity);

    @Update("UPDATE transport_order SET order_no = #{orderNo}, cargo_name = #{cargoName}, from_node = #{fromNode}, to_node = #{toNode}, carrier_name = #{carrierName}, vehicle_no = #{vehicleNo}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(TransportOrder entity);

    @Delete("DELETE FROM transport_order WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM transport_order")
    long countAll();

    @Update("UPDATE transport_order SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
