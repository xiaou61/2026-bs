package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Course;
import com.xiaou.entity.CourseStudent;
import com.xiaou.entity.User;
import com.xiaou.mapper.CourseMapper;
import com.xiaou.mapper.CourseStudentMapper;
import com.xiaou.mapper.UserMapper;
import com.xiaou.service.CourseStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseStudentServiceImpl extends ServiceImpl<CourseStudentMapper, CourseStudent> implements CourseStudentService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<User> getCourseStudents(Long courseId) {
        List<CourseStudent> courseStudents = this.list(
                new LambdaQueryWrapper<CourseStudent>()
                        .eq(CourseStudent::getCourseId, courseId));
        List<Long> studentIds = courseStudents.stream()
                .map(CourseStudent::getStudentId)
                .collect(Collectors.toList());
        if (studentIds.isEmpty()) {
            return List.of();
        }
        return userMapper.selectList(
                new LambdaQueryWrapper<User>()
                        .in(User::getId, studentIds));
    }

    @Override
    @Transactional
    public boolean selectCourse(Long studentId, Long courseId) {
        // 检查是否已选
        Long count = this.count(new LambdaQueryWrapper<CourseStudent>()
                .eq(CourseStudent::getStudentId, studentId)
                .eq(CourseStudent::getCourseId, courseId));
        if (count > 0) {
            return false;
        }
        
        CourseStudent cs = new CourseStudent();
        cs.setStudentId(studentId);
        cs.setCourseId(courseId);
        cs.setCreateTime(LocalDateTime.now());
        cs.setStatus(1);
        this.save(cs);
        
        // 更新课程学生数
        Course course = courseMapper.selectById(courseId);
        course.setStudentCount(course.getStudentCount() + 1);
        courseMapper.updateById(course);
        
        return true;
    }

    @Override
    @Transactional
    public boolean dropCourse(Long studentId, Long courseId) {
        boolean removed = this.remove(new LambdaQueryWrapper<CourseStudent>()
                .eq(CourseStudent::getStudentId, studentId)
                .eq(CourseStudent::getCourseId, courseId));
        
        if (removed) {
            // 更新课程学生数
            Course course = courseMapper.selectById(courseId);
            course.setStudentCount(Math.max(0, course.getStudentCount() - 1));
            courseMapper.updateById(course);
        }
        
        return removed;
    }
}
