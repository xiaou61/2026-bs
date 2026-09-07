package com.kindergarten.mapper;

import com.kindergarten.entity.ActivitySchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityScheduleMapper {
    List<ActivitySchedule> selectList(@Param("courseName") String courseName, @Param("teacherId") Long teacherId, @Param("termId") Long termId, @Param("classId") Long classId, @Param("status") Integer status);
    List<ActivitySchedule> selectTeacherList(@Param("teacherId") Long teacherId);
    List<ActivitySchedule> selectStudentList(@Param("studentId") Long studentId);
    ActivitySchedule selectById(@Param("id") Long id);
    long countAll();
    int insert(ActivitySchedule entity);
    int update(ActivitySchedule entity);
    int deleteById(@Param("id") Long id);
    int increaseSelectedCount(@Param("id") Long id);
}
