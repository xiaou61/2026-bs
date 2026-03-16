package com.teachres.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachres.common.BusinessException;
import com.teachres.entity.MathCourse;
import com.teachres.mapper.MathCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private MathCourseMapper courseMapper;

    public PageInfo<MathCourse> list(String courseName, Long categoryId, Long teacherId, Integer status,
                                     String term, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MathCourse> list = courseMapper.selectList(courseName, categoryId, teacherId, status, term);
        return new PageInfo<>(list);
    }

    public List<MathCourse> enabledList() {
        return courseMapper.selectEnabledList();
    }

    public MathCourse detail(Long id) {
        MathCourse course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }
        return course;
    }

    public void add(MathCourse course, Long userId) {
        if (!StringUtils.hasText(course.getCourseName()) || !StringUtils.hasText(course.getCourseCode())) {
            throw new BusinessException("课程名称和编码不能为空");
        }
        if (courseMapper.countByCode(course.getCourseCode()) > 0) {
            throw new BusinessException("课程编码已存在");
        }
        if (course.getTeacherId() == null) {
            course.setTeacherId(userId);
        }
        if (course.getCredit() == null) {
            course.setCredit(BigDecimal.ONE);
        }
        if (course.getStatus() == null) {
            course.setStatus(1);
        }
        courseMapper.insert(course);
    }

    public void update(MathCourse course) {
        if (course.getId() == null) {
            throw new BusinessException("参数错误");
        }
        courseMapper.update(course);
    }

    public void delete(Long id) {
        courseMapper.deleteById(id);
    }
}
