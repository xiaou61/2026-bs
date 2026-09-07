package com.course.mapper;

import com.course.entity.CourseEvaluation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseEvaluationMapper {
    List<CourseEvaluation> selectList(@Param("scheduleId") Long scheduleId, @Param("studentId") Long studentId, @Param("teacherId") Long teacherId);
    CourseEvaluation selectByScheduleAndStudent(@Param("scheduleId") Long scheduleId, @Param("studentId") Long studentId);
    List<CourseEvaluation> selectTeacherResult(@Param("teacherId") Long teacherId);
    int insert(CourseEvaluation entity);
}
