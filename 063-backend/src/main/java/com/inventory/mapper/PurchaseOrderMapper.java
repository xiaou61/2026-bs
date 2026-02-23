package com.inventory.mapper;

import com.inventory.entity.PurchaseOrder;
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
public interface PurchaseOrderMapper {

    @Select("<script>" +
            "select * from purchase_order where 1=1 " +
            "<if test='orderNo!=null and orderNo!=\"\"'>and order_no like concat('%',#{orderNo},'%')</if> " +
            "<if test='supplierId!=null'>and supplier_id=#{supplierId}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<PurchaseOrder> selectPageList(@Param("orderNo") String orderNo, @Param("supplierId") Long supplierId, @Param("status") Integer status);

    @Select("select * from purchase_order where id=#{id}")
    PurchaseOrder selectById(@Param("id") Long id);

    @Select("select * from purchase_order where order_no=#{orderNo}")
    PurchaseOrder selectByNo(@Param("orderNo") String orderNo);

    @Insert("insert into purchase_order(order_no,supplier_id,product_id,quantity,price,amount,status,remark,creator_id,audit_time) values(#{orderNo},#{supplierId},#{productId},#{quantity},#{price},#{amount},#{status},#{remark},#{creatorId},#{auditTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PurchaseOrder order);

    @Update("update purchase_order set supplier_id=#{supplierId},product_id=#{productId},quantity=#{quantity},price=#{price},amount=#{amount},status=#{status},remark=#{remark},audit_time=#{auditTime} where id=#{id}")
    int update(PurchaseOrder order);

    @Delete("delete from purchase_order where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Select("select ifnull(sum(amount),0) from purchase_order where status=1 and audit_time between #{start} and #{end}")
    BigDecimal sumAuditAmount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Select("select date(audit_time) as day, ifnull(sum(amount),0) as amount from purchase_order where status=1 and audit_time between #{start} and #{end} group by date(audit_time)")
    List<Map<String, Object>> dailyAmount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
