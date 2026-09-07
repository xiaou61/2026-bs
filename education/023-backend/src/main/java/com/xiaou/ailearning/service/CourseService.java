package com.xiaou.ailearning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.ailearning.common.PageResult;
import com.xiaou.ailearning.entity.Course;

import java.util.List;

public interface CourseService extends IService<Course> {
    
    PageResult<Course> getCourseList(int page, int size, String keyword);
    
    Course getCourseDetail(Long courseId);
    
    List<Course> getRecommendedCourses(Long userId, int limit);
    
    List<Course> getCoursesByCategory(Long categoryId);
    
    List<Course> getHotCourses(int limit);
    
    boolean enrollCourse(Long userId, Long courseId);
    
    boolean isEnrolled(Long userId, Long courseId);
    
    List<Course> getUserEnrolledCourses(Long userId);
}