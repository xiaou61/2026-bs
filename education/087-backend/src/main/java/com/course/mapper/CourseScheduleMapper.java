package com.course.mapper;

import com.course.entity.CourseSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseScheduleMapper {
    List<CourseSchedule> selectList(@Param("courseName") String courseName, @Param("teacherId") Long teacherId, @Param("termId") Long termId, @Param("classId") Long classId, @Param("status") Integer status);
    List<CourseSchedule> selectTeacherList(@Param("teacherId") Long teacherId);
    List<CourseSchedule> selectStudentList(@Param("studentId") Long studentId);
    CourseSchedule selectById(@Param("id") Long id);
    long countAll();
    int insert(CourseSchedule entity);
    int update(CourseSchedule entity);
    int deleteById(@Param("id") Long id);
    int increaseSelectedCount(@Param("id") Long id);
}
