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

    public AttendanceRecord getById(Long id) {
        return attendanceMapper.selectById(id);
    }

    public PageInfo<AttendanceRecord> list(Long scheduleId, Long studentId, Long userId, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AttendanceRecord> list = attendanceMapper.selectList(scheduleId, "student".equals(role) ? userId : studentId, "teacher".equals(role) ? userId : null);
        return new PageInfo<>(list);
    }

    public void add(AttendanceRecord entity, Long userId, String role) {
        authService.assertTeacher(role);
        if (entity == null || entity.getScheduleId() == null || entity.getCourseId() == null || entity.getStudentId() == null) {
            throw new BusinessException(400, "排课、课程、学生不能为空");
        }
        entity.setTeacherId(userId);
        attendanceMapper.insert(entity);
    }

    public void update(AttendanceRecord entity, Long userId, String role) {
        authService.assertTeacher(role);
        if (entity.getId() == null) {
            throw new BusinessException(400, "考勤ID不能为空");
        }
        AttendanceRecord exists = attendanceMapper.selectById(entity.getId());
        if (exists == null) {
            throw new BusinessException(404, "考勤记录不存在");
        }
        if ("teacher".equals(role) && !userId.equals(exists.getTeacherId())) {
            throw new BusinessException(403, "无权限修改该考勤记录");
        }
        entity.setTeacherId(exists.getTeacherId());
        attendanceMapper.update(entity);
    }
}
