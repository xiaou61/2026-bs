package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.CourseSchedule;

import java.util.List;

public interface CourseScheduleService extends IService<CourseSchedule> {
    List<CourseSchedule> getCourseSchedules(Long courseId);
    List<CourseSchedule> getTodaySchedules(Long userId, Integer role);
}
