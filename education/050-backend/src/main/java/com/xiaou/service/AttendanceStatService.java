package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.AttendanceStat;

import java.util.List;
import java.util.Map;

public interface AttendanceStatService extends IService<AttendanceStat> {
    AttendanceStat getStudentCourseStat(Long studentId, Long courseId);
    List<AttendanceStat> getStudentAllStats(Long studentId);
    List<AttendanceStat> getCourseAllStats(Long courseId);
    void refreshStat(Long studentId, Long courseId);
    Map<String, Object> getCourseStatSummary(Long courseId);
}
