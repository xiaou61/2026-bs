package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Course;
import com.xiaou.entity.CourseSchedule;
import com.xiaou.entity.CourseStudent;
import com.xiaou.mapper.CourseMapper;
import com.xiaou.mapper.CourseScheduleMapper;
import com.xiaou.mapper.CourseStudentMapper;
import com.xiaou.service.CourseScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseScheduleServiceImpl extends ServiceImpl<CourseScheduleMapper, CourseSchedule> implements CourseScheduleService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseStudentMapper courseStudentMapper;

    @Override
    public List<CourseSchedule> getCourseSchedules(Long courseId) {
        return this.list(new LambdaQueryWrapper<CourseSchedule>()
                .eq(CourseSchedule::getCourseId, courseId)
                .orderByAsc(CourseSchedule::getWeekDay)
                .orderByAsc(CourseSchedule::getStartTime));
    }

    @Override
    public List<CourseSchedule> getTodaySchedules(Long userId, Integer role) {
        // 获取今天是星期几（1-7）
        int weekDay = LocalDate.now().getDayOfWeek().getValue();
        
        List<Long> courseIds;
        if (role == 1) {
            // 教师：获取其教授的课程
            List<Course> courses = courseMapper.selectList(
                    new LambdaQueryWrapper<Course>()
                            .eq(Course::getTeacherId, userId));
            courseIds = courses.stream().map(Course::getId).collect(Collectors.toList());
        } else {
            // 学生：获取其选修的课程
            List<CourseStudent> courseStudents = courseStudentMapper.selectList(
                    new LambdaQueryWrapper<CourseStudent>()
                            .eq(CourseStudent::getStudentId, userId));
            courseIds = courseStudents.stream().map(CourseStudent::getCourseId).collect(Collectors.toList());
        }
        
        if (courseIds.isEmpty()) {
            return List.of();
        }
        
        return this.list(new LambdaQueryWrapper<CourseSchedule>()
                .in(CourseSchedule::getCourseId, courseIds)
                .eq(CourseSchedule::getWeekDay, weekDay)
                .orderByAsc(CourseSchedule::getStartTime));
    }
}
