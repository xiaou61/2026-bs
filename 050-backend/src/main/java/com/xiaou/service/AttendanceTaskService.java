package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.dto.CreateTaskDTO;
import com.xiaou.entity.AttendanceTask;

import java.util.List;
import java.util.Map;

public interface AttendanceTaskService extends IService<AttendanceTask> {
    AttendanceTask createTask(Long teacherId, CreateTaskDTO dto);
    void endTask(Long taskId, Long teacherId);
    IPage<AttendanceTask> pageTasksByCourse(Integer page, Integer size, Long courseId);
    List<AttendanceTask> getActiveTasksForStudent(Long studentId);
    Map<String, Object> getTaskDetail(Long taskId);
    String refreshQrCode(Long taskId, Long teacherId);
}
