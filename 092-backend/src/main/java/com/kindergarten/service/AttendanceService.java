package com.kindergarten.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kindergarten.common.BusinessException;
import com.kindergarten.entity.AttendanceRecord;
import com.kindergarten.mapper.AttendanceRecordMapper;
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
        List<AttendanceRecord> list = attendanceMapper.selectList(scheduleId, "parent".equals(role) ? userId : studentId, "teacher".equals(role) ? userId : null);
        return new PageInfo<>(list);
    }

    public void add(AttendanceRecord entity, Long userId, String role) {
        authService.assertTeacher(role);
        if (entity == null || entity.getScheduleId() == null || entity.getCourseId() == null || entity.getStudentId() == null) {
            throw new BusinessException("排课、活动、幼儿不能为空");
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
