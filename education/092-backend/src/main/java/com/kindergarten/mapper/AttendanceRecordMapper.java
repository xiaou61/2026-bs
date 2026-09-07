package com.kindergarten.mapper;

import com.kindergarten.entity.AttendanceRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttendanceRecordMapper {
    List<AttendanceRecord> selectList(@Param("scheduleId") Long scheduleId, @Param("studentId") Long studentId, @Param("teacherId") Long teacherId);
    AttendanceRecord selectById(@Param("id") Long id);
    int insert(AttendanceRecord entity);
    int update(AttendanceRecord entity);
    int deleteById(@Param("id") Long id);
}
