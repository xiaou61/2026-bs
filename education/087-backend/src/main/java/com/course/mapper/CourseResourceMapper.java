package com.course.mapper;

import com.course.entity.CourseResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseResourceMapper {
    List<CourseResource> selectList(@Param("scheduleId") Long scheduleId, @Param("teacherId") Long teacherId, @Param("keyword") String keyword);
    List<CourseResource> selectStudentList(@Param("studentId") Long studentId, @Param("keyword") String keyword);
    CourseResource selectById(@Param("id") Long id);
    int insert(CourseResource entity);
    int update(CourseResource entity);
    int deleteById(@Param("id") Long id);
}
