package com.property.mapper;

import com.property.entity.RepairOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface RepairOrderMapper {

    @Select("<script>" +
            "select * from repair_order where 1=1 " +
            "<if test='ownerId!=null'>and owner_id=#{ownerId}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "<if test='assigneeId!=null'>and assignee_id=#{assigneeId}</if> " +
            "<if test='orderNo!=null and orderNo!=\"\"'>and order_no like concat('%',#{orderNo},'%')</if> " +
            "order by id desc" +
            "</script>")
    List<RepairOrder> selectPageList(@Param("ownerId") Long ownerId, @Param("status") Integer status, @Param("assigneeId") Long assigneeId, @Param("orderNo") String orderNo);

    @Select("select * from repair_order where id=#{id}")
    RepairOrder selectById(@Param("id") Long id);

    @Insert("insert into repair_order(order_no,house_id,owner_id,title,content,status,assignee_id,reply) values(#{orderNo},#{houseId},#{ownerId},#{title},#{content},#{status},#{assigneeId},#{reply})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RepairOrder order);

    @Update("update repair_order set status=#{status},assignee_id=#{assigneeId},reply=#{reply} where id=#{id}")
    int updateStatus(RepairOrder order);

    @Delete("delete from repair_order where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Select("select count(1) from repair_order where status in (0,1)")
    Long countPending();

    @Select("select status, count(1) as total from repair_order group by status")
    List<Map<String, Object>> statusStats();
}
