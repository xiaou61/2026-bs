package com.gongkao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.entity.Course;
import com.gongkao.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public Page<Course> getList(int pageNum, int pageSize, String title, Long subjectId, Integer status) {
        Page<Course> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(title)) {
            wrapper.like("title", title);
        }
        if (subjectId != null) {
            wrapper.eq("subject_id", subjectId);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return courseMapper.selectPage(page, wrapper);
    }

    public List<Course> getPublicList(Long subjectId) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        if (subjectId != null) {
            wrapper.eq("subject_id", subjectId);
        }
        wrapper.orderByDesc("create_time");
        return courseMapper.selectList(wrapper);
    }

    public void add(Course course) {
        if (course.getStatus() == null) {
            course.setStatus(1);
        }
        if (course.getStudyHours() == null) {
            course.setStudyHours(0);
        }
        courseMapper.insert(course);
    }

    public void update(Course course) {
        courseMapper.updateById(course);
    }

    public void delete(Long id) {
        courseMapper.deleteById(id);
    }
}
