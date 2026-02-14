package com.student.mapper;

import com.student.entity.CourseEnroll;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface EnrollMapper {

    @Select("<script>" +
            "select e.* from course_enroll e left join campus_course c on e.course_id=c.id where 1=1 " +
            "<if test='courseId!=null'>and e.course_id=#{courseId}</if> " +
            "<if test='studentId!=null'>and e.student_id=#{studentId}</if> " +
            "<if test='status!=null'>and e.status=#{status}</if> " +
            "<if test='teacherId!=null'>and c.teacher_id=#{teacherId}</if> " +
            "order by id desc" +
            "</script>")
    List<CourseEnroll> selectPageList(@Param("courseId") Long courseId, @Param("studentId") Long studentId, @Param("status") Integer status, @Param("teacherId") Long teacherId);

    @Select("select * from course_enroll where id=#{id}")
    CourseEnroll selectById(@Param("id") Long id);

    @Select("select * from course_enroll where course_id=#{courseId} and student_id=#{studentId}")
    CourseEnroll selectByCourseAndStudent(@Param("courseId") Long courseId, @Param("studentId") Long studentId);

    @Insert("insert into course_enroll(course_id,student_id,status,score,remark) values(#{courseId},#{studentId},#{status},#{score},#{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CourseEnroll enroll);

    @Update("update course_enroll set status=#{status},score=#{score},remark=#{remark} where id=#{id}")
    int updateStatus(CourseEnroll enroll);

    @Delete("delete from course_enroll where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Delete("delete from course_enroll where course_id=#{courseId}")
    int deleteByCourseId(@Param("courseId") Long courseId);

    @Select("select count(1) from course_enroll")
    Long countAll();

    @Select("select date(create_time) as day, count(1) as total from course_enroll where create_time between #{start} and #{end} group by date(create_time)")
    List<Map<String, Object>> dailyCreatedCount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
