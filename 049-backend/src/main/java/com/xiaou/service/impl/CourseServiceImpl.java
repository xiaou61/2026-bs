package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Chapter;
import com.xiaou.entity.Course;
import com.xiaou.entity.Subject;
import com.xiaou.entity.User;
import com.xiaou.mapper.ChapterMapper;
import com.xiaou.mapper.CourseMapper;
import com.xiaou.mapper.SubjectMapper;
import com.xiaou.mapper.UserMapper;
import com.xiaou.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    private final ChapterMapper chapterMapper;
    private final SubjectMapper subjectMapper;
    private final UserMapper userMapper;

    @Override
    public Page<Course> getPage(int current, int size, Long subjectId, String keyword, Integer isFree) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getStatus, 1);
        if (subjectId != null) {
            wrapper.eq(Course::getSubjectId, subjectId);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Course::getTitle, keyword);
        }
        if (isFree != null) {
            wrapper.eq(Course::getIsFree, isFree);
        }
        wrapper.orderByDesc(Course::getStudentCount);
        Page<Course> page = page(new Page<>(current, size), wrapper);
        page.getRecords().forEach(this::fillCourseInfo);
        return page;
    }

    @Override
    public Course getDetail(Long id) {
        Course course = getById(id);
        if (course != null) {
            fillCourseInfo(course);
            course.setViewCount(course.getViewCount() + 1);
            updateById(course);
        }
        return course;
    }

    @Override
    public List<Chapter> getChapters(Long courseId) {
        return chapterMapper.selectList(new LambdaQueryWrapper<Chapter>()
                .eq(Chapter::getCourseId, courseId)
                .orderByAsc(Chapter::getSortOrder));
    }

    @Override
    public void addStudentCount(Long courseId) {
        Course course = getById(courseId);
        if (course != null) {
            course.setStudentCount(course.getStudentCount() + 1);
            updateById(course);
        }
    }

    private void fillCourseInfo(Course course) {
        if (course.getSubjectId() != null) {
            Subject subject = subjectMapper.selectById(course.getSubjectId());
            if (subject != null) course.setSubjectName(subject.getName());
        }
        if (course.getTeacherId() != null) {
            User teacher = userMapper.selectById(course.getTeacherId());
            if (teacher != null) course.setTeacherName(teacher.getNickname());
        }
    }
}
