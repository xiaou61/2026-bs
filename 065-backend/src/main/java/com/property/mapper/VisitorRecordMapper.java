package com.property.mapper;

import com.property.entity.VisitorRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface VisitorRecordMapper {

    @Select("<script>" +
            "select * from visitor_record where 1=1 " +
            "<if test='ownerId!=null'>and owner_id=#{ownerId}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "<if test='orderNo!=null and orderNo!=\"\"'>and order_no like concat('%',#{orderNo},'%')</if> " +
            "order by id desc" +
            "</script>")
    List<VisitorRecord> selectPageList(@Param("ownerId") Long ownerId, @Param("status") Integer status, @Param("orderNo") String orderNo);

    @Select("select * from visitor_record where id=#{id}")
    VisitorRecord selectById(@Param("id") Long id);

    @Insert("insert into visitor_record(order_no,owner_id,visitor_name,visitor_phone,visit_time,status,remark) values(#{orderNo},#{ownerId},#{visitorName},#{visitorPhone},#{visitTime},#{status},#{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(VisitorRecord record);

    @Update("update visitor_record set status=#{status},remark=#{remark} where id=#{id}")
    int updateStatus(VisitorRecord record);

    @Delete("delete from visitor_record where id=#{id}")
    int deleteById(@Param("id") Long id);
}
