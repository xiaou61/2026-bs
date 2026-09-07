package com.xiaou.service;

import com.xiaou.common.BusinessException;
import com.xiaou.entity.Course;
import com.xiaou.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public Course findById(Long id) {
        Course course = courseMapper.findById(id);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }
        return course;
    }

    public List<Course> findAll(String keyword, Long teacherId, String courseType) {
        return courseMapper.findAll(keyword, teacherId, courseType);
    }

    public List<Course> findByTeacherId(Long teacherId) {
        return courseMapper.findByTeacherId(teacherId);
    }

    public Course create(Course course) {
        Course existCourse = courseMapper.findByCourseNo(course.getCourseNo());
        if (existCourse != null) {
            throw new BusinessException("课程编号已存在");
        }

        if (course.getSelectedCount() == null) {
            course.setSelectedCount(0);
        }
        if (course.getStatus() == null) {
            course.setStatus(1);
        }

        courseMapper.insert(course);
        return course;
    }

    public Course update(Course course) {
        Course existCourse = courseMapper.findById(course.getId());
        if (existCourse == null) {
            throw new BusinessException("课程不存在");
        }
        courseMapper.update(course);
        return courseMapper.findById(course.getId());
    }

    public void delete(Long id) {
        Course existCourse = courseMapper.findById(id);
        if (existCourse == null) {
            throw new BusinessException("课程不存在");
        }
        courseMapper.deleteById(id);
    }

    public void updateSelectedCount(Long courseId, int increment) {
        courseMapper.updateSelectedCount(courseId, increment);
    }
}

