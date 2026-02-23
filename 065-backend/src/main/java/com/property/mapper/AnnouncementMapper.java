package com.property.mapper;

import com.property.entity.Announcement;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AnnouncementMapper {

    @Select("select * from announcement where status=1 order by id desc")
    List<Announcement> selectPublicList();

    @Select("<script>" +
            "select * from announcement where 1=1 " +
            "<if test='title!=null and title!=\"\"'>and title like concat('%',#{title},'%')</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<Announcement> selectPageList(@Param("title") String title, @Param("status") Integer status);

    @Select("select * from announcement where id=#{id}")
    Announcement selectById(@Param("id") Long id);

    @Insert("insert into announcement(title,content,admin_id,status) values(#{title},#{content},#{adminId},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Announcement announcement);

    @Update("update announcement set title=#{title},content=#{content},admin_id=#{adminId},status=#{status} where id=#{id}")
    int update(Announcement announcement);

    @Delete("delete from announcement where id=#{id}")
    int deleteById(@Param("id") Long id);
}
