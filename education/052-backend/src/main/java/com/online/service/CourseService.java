package com.online.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.entity.*;
import com.online.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService extends ServiceImpl<CourseMapper, Course> {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private VideoMapper videoMapper;

    public Page<Course> getList(Integer pageNum, Integer pageSize, Long categoryId, String keyword, String orderBy) {
        Page<Course> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<Course>()
                .eq(Course::getStatus, 1);
        if (categoryId != null) {
            wrapper.eq(Course::getCategoryId, categoryId);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Course::getTitle, keyword);
        }
        if ("hot".equals(orderBy)) {
            wrapper.orderByDesc(Course::getLearnCount);
        } else if ("score".equals(orderBy)) {
            wrapper.orderByDesc(Course::getScore);
        } else {
            wrapper.orderByDesc(Course::getCreateTime);
        }
        Page<Course> result = this.page(page, wrapper);
        result.getRecords().forEach(this::fillCourseInfo);
        return result;
    }

    public List<Course> getRecommend() {
        List<Course> list = this.list(new LambdaQueryWrapper<Course>()
                .eq(Course::getStatus, 1)
                .orderByDesc(Course::getScore)
                .last("limit 6"));
        list.forEach(this::fillCourseInfo);
        return list;
    }

    public List<Course> getHot() {
        List<Course> list = this.list(new LambdaQueryWrapper<Course>()
                .eq(Course::getStatus, 1)
                .orderByDesc(Course::getLearnCount)
                .last("limit 6"));
        list.forEach(this::fillCourseInfo);
        return list;
    }

    public List<Course> getLatest() {
        List<Course> list = this.list(new LambdaQueryWrapper<Course>()
                .eq(Course::getStatus, 1)
                .orderByDesc(Course::getCreateTime)
                .last("limit 6"));
        list.forEach(this::fillCourseInfo);
        return list;
    }

    public Course getDetail(Long id) {
        Course course = this.getById(id);
        if (course != null) {
            fillCourseInfo(course);
        }
        return course;
    }

    public List<Chapter> getChapters(Long courseId) {
        List<Chapter> chapters = chapterMapper.selectList(new LambdaQueryWrapper<Chapter>()
                .eq(Chapter::getCourseId, courseId)
                .orderByAsc(Chapter::getSort));
        chapters.forEach(chapter -> {
            List<Video> videos = videoMapper.selectList(new LambdaQueryWrapper<Video>()
                    .eq(Video::getChapterId, chapter.getId())
                    .orderByAsc(Video::getSort));
            chapter.setVideos(videos);
        });
        return chapters;
    }

    private void fillCourseInfo(Course course) {
        if (course.getCategoryId() != null) {
            Category category = categoryMapper.selectById(course.getCategoryId());
            if (category != null) {
                course.setCategoryName(category.getName());
            }
        }
        if (course.getTeacherId() != null) {
            User teacher = userMapper.selectById(course.getTeacherId());
            if (teacher != null) {
                course.setTeacherName(teacher.getNickname());
            }
        }
    }
}
