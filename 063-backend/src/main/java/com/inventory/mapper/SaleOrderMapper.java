package com.inventory.mapper;

import com.inventory.entity.SaleOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface SaleOrderMapper {

    @Select("<script>" +
            "select * from sale_order where 1=1 " +
            "<if test='orderNo!=null and orderNo!=\"\"'>and order_no like concat('%',#{orderNo},'%')</if> " +
            "<if test='customerId!=null'>and customer_id=#{customerId}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<SaleOrder> selectPageList(@Param("orderNo") String orderNo, @Param("customerId") Long customerId, @Param("status") Integer status);

    @Select("select * from sale_order where id=#{id}")
    SaleOrder selectById(@Param("id") Long id);

    @Select("select * from sale_order where order_no=#{orderNo}")
    SaleOrder selectByNo(@Param("orderNo") String orderNo);

    @Insert("insert into sale_order(order_no,customer_id,product_id,quantity,price,amount,status,remark,creator_id,audit_time) values(#{orderNo},#{customerId},#{productId},#{quantity},#{price},#{amount},#{status},#{remark},#{creatorId},#{auditTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SaleOrder order);

    @Update("update sale_order set customer_id=#{customerId},product_id=#{productId},quantity=#{quantity},price=#{price},amount=#{amount},status=#{status},remark=#{remark},audit_time=#{auditTime} where id=#{id}")
    int update(SaleOrder order);

    @Delete("delete from sale_order where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Select("select ifnull(sum(amount),0) from sale_order where status=1 and audit_time between #{start} and #{end}")
    BigDecimal sumAuditAmount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Select("select date(audit_time) as day, ifnull(sum(amount),0) as amount from sale_order where status=1 and audit_time between #{start} and #{end} group by date(audit_time)")
    List<Map<String, Object>> dailyAmount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
