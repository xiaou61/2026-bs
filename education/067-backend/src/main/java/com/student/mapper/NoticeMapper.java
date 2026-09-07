package com.student.mapper;

import com.student.entity.Notice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NoticeMapper {

    @Select("<script>" +
            "select * from campus_notice where 1=1 " +
            "<if test='title!=null and title!=\"\"'>and title like concat('%',#{title},'%')</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<Notice> selectPageList(@Param("title") String title, @Param("status") Integer status);

    @Select("select * from campus_notice where status=1 order by id desc")
    List<Notice> selectEnabledList();

    @Select("select * from campus_notice where id=#{id}")
    Notice selectById(@Param("id") Long id);

    @Insert("insert into campus_notice(title,content,publisher_id,status) values(#{title},#{content},#{publisherId},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Notice notice);

    @Update("update campus_notice set title=#{title},content=#{content},publisher_id=#{publisherId},status=#{status} where id=#{id}")
    int update(Notice notice);

    @Delete("delete from campus_notice where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Select("select count(1) from campus_notice")
    Long countAll();
}
