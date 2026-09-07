package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Course;
import com.xiaou.entity.CourseSchedule;
import com.xiaou.entity.CourseStudent;
import com.xiaou.entity.User;
import com.xiaou.mapper.CourseMapper;
import com.xiaou.mapper.CourseScheduleMapper;
import com.xiaou.mapper.CourseStudentMapper;
import com.xiaou.mapper.UserMapper;
import com.xiaou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseStudentMapper courseStudentMapper;

    @Autowired
    private CourseScheduleMapper courseScheduleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<Course> pageCourses(Integer page, Integer size, Long semesterId, Long teacherId, String keyword) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if (semesterId != null) {
            wrapper.eq(Course::getSemesterId, semesterId);
        }
        if (teacherId != null) {
            wrapper.eq(Course::getTeacherId, teacherId);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Course::getName, keyword)
                    .or().like(Course::getCourseCode, keyword));
        }
        wrapper.orderByDesc(Course::getCreateTime);
        return this.page(new Page<>(page, size), wrapper);
    }

    @Override
    public List<Course> getTeacherCourses(Long teacherId, Long semesterId) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getTeacherId, teacherId);
        if (semesterId != null) {
            wrapper.eq(Course::getSemesterId, semesterId);
        }
        return this.list(wrapper);
    }

    @Override
    public List<Course> getStudentCourses(Long studentId, Long semesterId) {
        // 先查询学生选的课程ID
        List<CourseStudent> courseStudents = courseStudentMapper.selectList(
                new LambdaQueryWrapper<CourseStudent>()
                        .eq(CourseStudent::getStudentId, studentId));
        List<Long> courseIds = courseStudents.stream()
                .map(CourseStudent::getCourseId)
                .collect(Collectors.toList());
        if (courseIds.isEmpty()) {
            return List.of();
        }
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Course::getId, courseIds);
        if (semesterId != null) {
            wrapper.eq(Course::getSemesterId, semesterId);
        }
        return this.list(wrapper);
    }

    @Override
    public Map<String, Object> getCourseDetail(Long courseId) {
        Map<String, Object> result = new HashMap<>();
        Course course = this.getById(courseId);
        result.put("course", course);
        
        // 获取教师信息
        if (course != null && course.getTeacherId() != null) {
            User teacher = userMapper.selectById(course.getTeacherId());
            result.put("teacher", teacher);
        }
        
        // 获取课程安排
        List<CourseSchedule> schedules = courseScheduleMapper.selectList(
                new LambdaQueryWrapper<CourseSchedule>()
                        .eq(CourseSchedule::getCourseId, courseId));
        result.put("schedules", schedules);
        
        // 获取选课学生数
        Long studentCount = courseStudentMapper.selectCount(
                new LambdaQueryWrapper<CourseStudent>()
                        .eq(CourseStudent::getCourseId, courseId));
        result.put("studentCount", studentCount);
        
        return result;
    }
}
