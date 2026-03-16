package com.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.course.common.BusinessException;
import com.course.entity.AttendanceRecord;
import com.course.mapper.AttendanceRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRecordMapper attendanceMapper;

    @Autowired
    private AuthService authService;

    public PageInfo<AttendanceRecord> list(Long scheduleId, Long studentId, Long userId, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AttendanceRecord> list = attendanceMapper.selectList(scheduleId, "student".equals(role) ? userId : studentId, "teacher".equals(role) ? userId : null);
        return new PageInfo<>(list);
    }

    public void add(AttendanceRecord entity, Long userId, String role) {
        authService.assertTeacher(role);
        if (entity == null || entity.getScheduleId() == null || entity.getCourseId() == null || entity.getStudentId() == null) {
            throw new BusinessException("排课、课程、学生不能为空");
        }
        entity.setTeacherId(userId);
        attendanceMapper.insert(entity);
    }

    public void update(AttendanceRecord entity, String role) {
        authService.assertTeacher(role);
        if (entity.getId() == null) {
            throw new BusinessException("考勤ID不能为空");
        }
        attendanceMapper.update(entity);
    }
}
