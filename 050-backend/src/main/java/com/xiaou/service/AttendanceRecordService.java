package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.dto.SignDTO;
import com.xiaou.entity.AttendanceRecord;

import java.util.List;
import java.util.Map;

public interface AttendanceRecordService extends IService<AttendanceRecord> {
    AttendanceRecord sign(Long studentId, SignDTO dto);
    IPage<AttendanceRecord> pageRecordsByTask(Integer page, Integer size, Long taskId);
    List<AttendanceRecord> getStudentRecords(Long studentId, Long courseId);
    Map<String, Object> getTaskSignStats(Long taskId);
}
