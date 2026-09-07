package com.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.course.common.BusinessException;
import com.course.entity.CourseSchedule;
import com.course.mapper.CourseScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private CourseScheduleMapper scheduleMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<CourseSchedule> list(String courseName, Long teacherId, Long termId, Long classId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CourseSchedule> list = scheduleMapper.selectList(courseName, teacherId, termId, classId, status);
        return new PageInfo<>(list);
    }

    public List<CourseSchedule> teacherList(Long teacherId) {
        return scheduleMapper.selectTeacherList(teacherId);
    }

    public List<CourseSchedule> studentList(Long studentId) {
        return scheduleMapper.selectStudentList(studentId);
    }

    public CourseSchedule getById(Long id) {
        return scheduleMapper.selectById(id);
    }

    public void add(CourseSchedule entity, String role) {
        authService.assertAdmin(role);
        validate(entity);
        if (entity.getSelectedCount() == null) {
            entity.setSelectedCount(0);
        }
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        scheduleMapper.insert(entity);
    }

    public void update(CourseSchedule entity, String role) {
        authService.assertAdmin(role);
        if (entity.getId() == null) {
            throw new BusinessException("排课ID不能为空");
        }
        validate(entity);
        scheduleMapper.update(entity);
    }

    public void delete(Long id, String role) {
        authService.assertAdmin(role);
        scheduleMapper.deleteById(id);
    }

    public void increaseSelectedCount(Long id) {
        scheduleMapper.increaseSelectedCount(id);
    }

    private void validate(CourseSchedule entity) {
        if (entity == null || entity.getCourseId() == null || entity.getTermId() == null || entity.getTeacherId() == null) {
            throw new BusinessException("课程、学期、教师不能为空");
        }
    }
}
