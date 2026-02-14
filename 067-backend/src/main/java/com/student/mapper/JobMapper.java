package com.student.mapper;

import com.student.entity.Job;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface JobMapper {

    @Select("<script>" +
            "select * from intern_job where 1=1 " +
            "<if test='keyword!=null and keyword!=\"\"'>and (title like concat('%',#{keyword},'%') or company like concat('%',#{keyword},'%'))</if> " +
            "<if test='publisherId!=null'>and publisher_id=#{publisherId}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<Job> selectPageList(@Param("keyword") String keyword, @Param("publisherId") Long publisherId, @Param("status") Integer status);

    @Select("<script>" +
            "select * from intern_job where 1=1 " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<Job> selectList(@Param("status") Integer status);

    @Select("select * from intern_job where id=#{id}")
    Job selectById(@Param("id") Long id);

    @Insert("insert into intern_job(title,company,city,salary,deadline,publisher_id,status,description) values(#{title},#{company},#{city},#{salary},#{deadline},#{publisherId},#{status},#{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Job job);

    @Update("update intern_job set title=#{title},company=#{company},city=#{city},salary=#{salary},deadline=#{deadline},publisher_id=#{publisherId},status=#{status},description=#{description} where id=#{id}")
    int update(Job job);

    @Delete("delete from intern_job where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Select("select count(1) from intern_job")
    Long countAll();
}
