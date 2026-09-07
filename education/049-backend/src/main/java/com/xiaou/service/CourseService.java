package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Course;
import com.xiaou.entity.Chapter;
import java.util.List;

public interface CourseService extends IService<Course> {
    Page<Course> getPage(int current, int size, Long subjectId, String keyword, Integer isFree);
    Course getDetail(Long id);
    List<Chapter> getChapters(Long courseId);
    void addStudentCount(Long courseId);
}
