package com.property.mapper;

import com.property.entity.FeeOrder;
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
public interface FeeOrderMapper {

    @Select("<script>" +
            "select * from fee_order where 1=1 " +
            "<if test='ownerId!=null'>and owner_id=#{ownerId}</if> " +
            "<if test='houseId!=null'>and house_id=#{houseId}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "<if test='feeMonth!=null and feeMonth!=\"\"'>and fee_month=#{feeMonth}</if> " +
            "<if test='orderNo!=null and orderNo!=\"\"'>and order_no like concat('%',#{orderNo},'%')</if> " +
            "order by id desc" +
            "</script>")
    List<FeeOrder> selectPageList(@Param("ownerId") Long ownerId, @Param("houseId") Long houseId, @Param("status") Integer status, @Param("feeMonth") String feeMonth, @Param("orderNo") String orderNo);

    @Select("select * from fee_order where id=#{id}")
    FeeOrder selectById(@Param("id") Long id);

    @Select("select count(1) from fee_order where status=0")
    Long countUnpaid();

    @Insert("insert into fee_order(order_no,house_id,owner_id,amount,fee_month,status,pay_time,remark,creator_id) values(#{orderNo},#{houseId},#{ownerId},#{amount},#{feeMonth},#{status},#{payTime},#{remark},#{creatorId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FeeOrder order);

    @Update("update fee_order set house_id=#{houseId},owner_id=#{ownerId},amount=#{amount},fee_month=#{feeMonth},status=#{status},pay_time=#{payTime},remark=#{remark} where id=#{id}")
    int update(FeeOrder order);

    @Delete("delete from fee_order where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Select("select ifnull(sum(amount),0) from fee_order where status=1 and pay_time between #{start} and #{end}")
    BigDecimal sumPaidAmount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Select("select date(pay_time) as day, ifnull(sum(amount),0) as amount from fee_order where status=1 and pay_time between #{start} and #{end} group by date(pay_time)")
    List<Map<String, Object>> dailyPaidAmount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
