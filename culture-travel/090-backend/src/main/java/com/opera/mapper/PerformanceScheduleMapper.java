package com.opera.mapper;

import com.opera.entity.PerformanceSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PerformanceScheduleMapper {
    List<PerformanceSchedule> selectList(@Param("courseName") String courseName, @Param("artistId") Long artistId, @Param("termId") Long termId, @Param("classId") Long classId, @Param("status") Integer status);
    List<PerformanceSchedule> selectTeacherList(@Param("artistId") Long artistId);
    List<PerformanceSchedule> selectStudentList(@Param("memberId") Long memberId);
    PerformanceSchedule selectById(@Param("id") Long id);
    long countAll();
    int insert(PerformanceSchedule entity);
    int update(PerformanceSchedule entity);
    int deleteById(@Param("id") Long id);
    int increaseSelectedCount(@Param("id") Long id);
}


