package com.course.mapper;

import com.course.entity.CourseSelection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseSelectionMapper {
    List<CourseSelection> selectList(@Param("scheduleId") Long scheduleId, @Param("studentId") Long studentId, @Param("selectStatus") Integer selectStatus, @Param("teacherId") Long teacherId);
    CourseSelection selectById(@Param("id") Long id);
    CourseSelection selectByScheduleAndStudent(@Param("scheduleId") Long scheduleId, @Param("studentId") Long studentId);
    long countAll();
    int insert(CourseSelection entity);
    int update(CourseSelection entity);
    int deleteById(@Param("id") Long id);
}
