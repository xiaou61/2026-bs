package com.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.course.common.BusinessException;
import com.course.entity.CourseInfo;
import com.course.mapper.CourseInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseInfoMapper courseMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<CourseInfo> list(String courseName, Long teacherId, Long termId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CourseInfo> list = courseMapper.selectList(courseName, teacherId, termId, status);
        return new PageInfo<>(list);
    }

    public List<CourseInfo> enabledList() {
        return courseMapper.selectEnabled();
    }

    public void add(CourseInfo entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        courseMapper.insert(entity);
    }

    public void update(CourseInfo entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("课程ID不能为空");
        }
        validate(entity);
        courseMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        courseMapper.deleteById(id);
    }

    public CourseInfo getById(Long id) {
        return courseMapper.selectById(id);
    }

    private void validate(CourseInfo entity) {
        if (entity == null || !StringUtils.hasText(entity.getCourseName()) || !StringUtils.hasText(entity.getCourseCode())) {
            throw new BusinessException("课程名称和课程编码不能为空");
        }
    }
}
