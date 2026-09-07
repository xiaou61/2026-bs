package com.property.mapper;

import com.property.entity.Complaint;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ComplaintMapper {

    @Select("<script>" +
            "select * from complaint where 1=1 " +
            "<if test='ownerId!=null'>and owner_id=#{ownerId}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "<if test='orderNo!=null and orderNo!=\"\"'>and order_no like concat('%',#{orderNo},'%')</if> " +
            "order by id desc" +
            "</script>")
    List<Complaint> selectPageList(@Param("ownerId") Long ownerId, @Param("status") Integer status, @Param("orderNo") String orderNo);

    @Select("select * from complaint where id=#{id}")
    Complaint selectById(@Param("id") Long id);

    @Insert("insert into complaint(order_no,owner_id,title,content,status,reply) values(#{orderNo},#{ownerId},#{title},#{content},#{status},#{reply})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Complaint complaint);

    @Update("update complaint set status=#{status},reply=#{reply} where id=#{id}")
    int updateReply(Complaint complaint);

    @Delete("delete from complaint where id=#{id}")
    int deleteById(@Param("id") Long id);
}
