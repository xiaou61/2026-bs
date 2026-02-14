package com.student.mapper;

import com.student.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("<script>" +
            "select * from campus_course where 1=1 " +
            "<if test='name!=null and name!=\"\"'>and name like concat('%',#{name},'%')</if> " +
            "<if test='teacherId!=null'>and teacher_id=#{teacherId}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<Course> selectPageList(@Param("name") String name, @Param("teacherId") Long teacherId, @Param("status") Integer status);

    @Select("<script>" +
            "select * from campus_course where 1=1 " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<Course> selectList(@Param("status") Integer status);

    @Select("select * from campus_course where id=#{id}")
    Course selectById(@Param("id") Long id);

    @Insert("insert into campus_course(name,teacher_id,credit,location,max_student,selected_count,start_date,end_date,status,description) values(#{name},#{teacherId},#{credit},#{location},#{maxStudent},#{selectedCount},#{startDate},#{endDate},#{status},#{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Course course);

    @Update("update campus_course set name=#{name},teacher_id=#{teacherId},credit=#{credit},location=#{location},max_student=#{maxStudent},selected_count=#{selectedCount},start_date=#{startDate},end_date=#{endDate},status=#{status},description=#{description} where id=#{id}")
    int update(Course course);

    @Update("update campus_course set selected_count=#{selectedCount} where id=#{id}")
    int updateSelectedCount(@Param("id") Long id, @Param("selectedCount") Integer selectedCount);

    @Delete("delete from campus_course where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Select("select count(1) from campus_course")
    Long countAll();
}
