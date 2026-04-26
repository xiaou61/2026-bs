package com.programming.learning.service;

import com.programming.learning.common.PageResult;
import com.programming.learning.entity.Course;
import com.programming.learning.exception.BusinessException;
import com.programming.learning.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程Service
 */
@Service
public class CourseService {

    private final CourseMapper courseMapper;

    public CourseService(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    public PageResult<Course> listCourses(Integer pageNum, Integer pageSize, String category) {
        int safePageNum = pageNum == null || pageNum < 1 ? 1 : pageNum;
        int safePageSize = pageSize == null || pageSize < 1 ? 10 : Math.min(pageSize, 50);
        int offset = (safePageNum - 1) * safePageSize;

        List<Course> courses;
        Long total;
        if (category != null && !category.isBlank()) {
            courses = courseMapper.selectByCategory(category, offset, safePageSize);
            total = courseMapper.countByCategory(category);
        } else {
            courses = courseMapper.selectAll(offset, safePageSize);
            total = courseMapper.countAll();
        }

        return PageResult.of(courses, total, safePageNum, safePageSize);
    }

    public List<Course> listHotCourses(Integer limit) {
        int safeLimit = limit == null || limit < 1 ? 5 : Math.min(limit, 20);
        return courseMapper.selectHot(safeLimit);
    }

    public Course getCourse(Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException(404, "课程不存在");
        }
        courseMapper.incrementViewCount(id);
        return course;
    }
}
