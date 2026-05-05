package com.livecommerce.mapper;

import com.livecommerce.entity.LiveOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LiveOrderMapper {
    @Select({
        "<script>",
        "SELECT * FROM live_order",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (order_no LIKE CONCAT('%',#{keyword},'%') OR session_title LIKE CONCAT('%',#{keyword},'%') OR product_name LIKE CONCAT('%',#{keyword},'%') OR buyer_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<LiveOrder> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM live_order WHERE id = #{id}")
    LiveOrder selectById(Long id);

    @Insert("INSERT INTO live_order (order_no, session_title, product_name, buyer_name, order_amount, pay_channel, status, created_time, updated_time) VALUES (#{orderNo}, #{sessionTitle}, #{productName}, #{buyerName}, #{orderAmount}, #{payChannel}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LiveOrder entity);

    @Update("UPDATE live_order SET order_no = #{orderNo}, session_title = #{sessionTitle}, product_name = #{productName}, buyer_name = #{buyerName}, order_amount = #{orderAmount}, pay_channel = #{payChannel}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(LiveOrder entity);

    @Delete("DELETE FROM live_order WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM live_order")
    long countAll();

    @Update("UPDATE live_order SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
