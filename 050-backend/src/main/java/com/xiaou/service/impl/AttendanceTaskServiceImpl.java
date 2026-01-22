package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.dto.CreateTaskDTO;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import com.xiaou.service.AttendanceTaskService;
import com.xiaou.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceTaskServiceImpl extends ServiceImpl<AttendanceTaskMapper, AttendanceTask> implements AttendanceTaskService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseStudentMapper courseStudentMapper;

    @Autowired
    private AttendanceRecordMapper attendanceRecordMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageService messageService;

    @Override
    @Transactional
    public AttendanceTask createTask(Long teacherId, CreateTaskDTO dto) {
        AttendanceTask task = new AttendanceTask();
        task.setCourseId(dto.getCourseId());
        task.setScheduleId(dto.getScheduleId());
        task.setTeacherId(teacherId);
        task.setTitle(dto.getTitle());
        task.setSignType(dto.getSignType());
        task.setStartTime(LocalDateTime.now());
        task.setEndTime(LocalDateTime.now().plusMinutes(dto.getDuration()));
        task.setDuration(dto.getDuration());
        // lateTime should be a LocalDateTime, calculate based on lateMinutes
        if (dto.getLateMinutes() != null) {
            task.setLateTime(LocalDateTime.now().plusMinutes(dto.getLateMinutes()));
        }
        task.setStatus(1); // 进行中
        
        // 定位签到设置
        if (dto.getSignType() == 2) {
            task.setLatitude(dto.getLatitude());
            task.setLongitude(dto.getLongitude());
            task.setAddress(dto.getAddress());
            task.setDistance(dto.getDistance());
        }
        
        // 二维码签到
        if (dto.getSignType() == 3) {
            task.setQrCode(UUID.randomUUID().toString());
        }
        
        // 数字签到
        if (dto.getSignType() == 4) {
            task.setSignCode(String.format("%06d", new Random().nextInt(1000000)));
        }
        
        task.setCreateTime(LocalDateTime.now());
        this.save(task);
        
        // 为课程所有学生创建待签到记录
        List<CourseStudent> students = courseStudentMapper.selectList(
                new LambdaQueryWrapper<CourseStudent>()
                        .eq(CourseStudent::getCourseId, dto.getCourseId()));
        for (CourseStudent cs : students) {
            AttendanceRecord record = new AttendanceRecord();
            record.setTaskId(task.getId());
            record.setStudentId(cs.getStudentId());
            record.setStatus(0); // 未签到
            record.setCreateTime(LocalDateTime.now());
            attendanceRecordMapper.insert(record);
            
            // 发送签到提醒消息
            Course course = courseMapper.selectById(dto.getCourseId());
            messageService.sendMessage(cs.getStudentId(), 1, "签到提醒",
                    "课程【" + course.getName() + "】已发起签到，请尽快完成签到");
        }
        
        return task;
    }

    @Override
    public void endTask(Long taskId, Long teacherId) {
        AttendanceTask task = this.getById(taskId);
        if (task != null && task.getTeacherId().equals(teacherId)) {
            task.setStatus(2); // 已结束
            task.setEndTime(LocalDateTime.now());
            this.updateById(task);
        }
    }

    @Override
    public IPage<AttendanceTask> pageTasksByCourse(Integer page, Integer size, Long courseId) {
        return this.page(new Page<>(page, size),
                new LambdaQueryWrapper<AttendanceTask>()
                        .eq(AttendanceTask::getCourseId, courseId)
                        .orderByDesc(AttendanceTask::getCreateTime));
    }

    @Override
    public List<AttendanceTask> getActiveTasksForStudent(Long studentId) {
        // 获取学生选修的课程
        List<CourseStudent> courseStudents = courseStudentMapper.selectList(
                new LambdaQueryWrapper<CourseStudent>()
                        .eq(CourseStudent::getStudentId, studentId));
        List<Long> courseIds = courseStudents.stream()
                .map(CourseStudent::getCourseId)
                .collect(Collectors.toList());
        
        if (courseIds.isEmpty()) {
            return List.of();
        }
        
        // 获取进行中的签到任务
        return this.list(new LambdaQueryWrapper<AttendanceTask>()
                .in(AttendanceTask::getCourseId, courseIds)
                .eq(AttendanceTask::getStatus, 1)
                .gt(AttendanceTask::getEndTime, LocalDateTime.now())
                .orderByDesc(AttendanceTask::getCreateTime));
    }

    @Override
    public Map<String, Object> getTaskDetail(Long taskId) {
        Map<String, Object> result = new HashMap<>();
        AttendanceTask task = this.getById(taskId);
        result.put("task", task);
        
        if (task != null) {
            Course course = courseMapper.selectById(task.getCourseId());
            result.put("course", course);
            
            User teacher = userMapper.selectById(task.getTeacherId());
            result.put("teacher", teacher);
            
            // 统计签到情况
            Long total = attendanceRecordMapper.selectCount(
                    new LambdaQueryWrapper<AttendanceRecord>()
                            .eq(AttendanceRecord::getTaskId, taskId));
            Long signed = attendanceRecordMapper.selectCount(
                    new LambdaQueryWrapper<AttendanceRecord>()
                            .eq(AttendanceRecord::getTaskId, taskId)
                            .ne(AttendanceRecord::getStatus, 0));
            result.put("totalCount", total);
            result.put("signedCount", signed);
        }
        
        return result;
    }

    @Override
    public String refreshQrCode(Long taskId, Long teacherId) {
        AttendanceTask task = this.getById(taskId);
        if (task != null && task.getTeacherId().equals(teacherId) && task.getSignType() == 3) {
            String newQrCode = UUID.randomUUID().toString();
            task.setQrCode(newQrCode);
            this.updateById(task);
            return newQrCode;
        }
        return null;
    }
}
