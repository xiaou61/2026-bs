package com.kindergarten.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kindergarten.common.BusinessException;
import com.kindergarten.entity.ActivitySchedule;
import com.kindergarten.mapper.ActivityScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ActivityScheduleMapper scheduleMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<ActivitySchedule> list(String courseName, Long teacherId, Long termId, Long classId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ActivitySchedule> list = scheduleMapper.selectList(courseName, teacherId, termId, classId, status);
        return new PageInfo<>(list);
    }

    public List<ActivitySchedule> teacherList(Long teacherId) {
        return scheduleMapper.selectTeacherList(teacherId);
    }

    public List<ActivitySchedule> studentList(Long studentId) {
        return scheduleMapper.selectStudentList(studentId);
    }

    public ActivitySchedule getById(Long id) {
        return scheduleMapper.selectById(id);
    }

    public void add(ActivitySchedule entity, String role) {
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

    public void update(ActivitySchedule entity, String role) {
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

    private void validate(ActivitySchedule entity) {
        if (entity == null || entity.getCourseId() == null || entity.getTermId() == null || entity.getTeacherId() == null) {
            throw new BusinessException("活动、学期、教师不能为空");
        }
    }
}
