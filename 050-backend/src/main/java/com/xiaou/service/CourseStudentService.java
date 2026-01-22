package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.CourseStudent;
import com.xiaou.entity.User;

import java.util.List;

public interface CourseStudentService extends IService<CourseStudent> {
    List<User> getCourseStudents(Long courseId);
    boolean selectCourse(Long studentId, Long courseId);
    boolean dropCourse(Long studentId, Long courseId);
}
