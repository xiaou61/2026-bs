package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Course;

import java.util.List;
import java.util.Map;

public interface CourseService extends IService<Course> {
    IPage<Course> pageCourses(Integer page, Integer size, Long semesterId, Long teacherId, String keyword);
    List<Course> getTeacherCourses(Long teacherId, Long semesterId);
    List<Course> getStudentCourses(Long studentId, Long semesterId);
    Map<String, Object> getCourseDetail(Long courseId);
}
