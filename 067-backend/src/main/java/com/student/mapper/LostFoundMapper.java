package com.student.mapper;

import com.student.entity.LostFound;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LostFoundMapper {

    @Select("<script>" +
            "select * from lost_found where 1=1 " +
            "<if test='keyword!=null and keyword!=\"\"'>and (title like concat('%',#{keyword},'%') or item_name like concat('%',#{keyword},'%'))</if> " +
            "<if test='type!=null'>and type=#{type}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "<if test='publisherId!=null'>and publisher_id=#{publisherId}</if> " +
            "order by id desc" +
            "</script>")
    List<LostFound> selectPageList(@Param("keyword") String keyword, @Param("type") Integer type, @Param("status") Integer status, @Param("publisherId") Long publisherId);

    @Select("select * from lost_found where id=#{id}")
    LostFound selectById(@Param("id") Long id);

    @Insert("insert into lost_found(type,title,item_name,contact,location,happen_time,description,image_url,status,publisher_id) values(#{type},#{title},#{itemName},#{contact},#{location},#{happenTime},#{description},#{imageUrl},#{status},#{publisherId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LostFound lostFound);

    @Update("update lost_found set type=#{type},title=#{title},item_name=#{itemName},contact=#{contact},location=#{location},happen_time=#{happenTime},description=#{description},image_url=#{imageUrl},status=#{status} where id=#{id}")
    int update(LostFound lostFound);

    @Update("update lost_found set status=#{status} where id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    @Delete("delete from lost_found where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Select("select count(1) from lost_found")
    Long countAll();
}
