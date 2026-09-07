package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.AttendanceRecord;
import com.xiaou.entity.Course;
import com.xiaou.entity.LeaveRequest;
import com.xiaou.mapper.AttendanceRecordMapper;
import com.xiaou.mapper.CourseMapper;
import com.xiaou.mapper.LeaveRequestMapper;
import com.xiaou.service.LeaveRequestService;
import com.xiaou.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LeaveRequestServiceImpl extends ServiceImpl<LeaveRequestMapper, LeaveRequest> implements LeaveRequestService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private AttendanceRecordMapper attendanceRecordMapper;

    @Autowired
    private MessageService messageService;

    @Override
    public LeaveRequest submitLeave(LeaveRequest request) {
        request.setStatus(0); // 待审核
        request.setCreateTime(LocalDateTime.now());
        this.save(request);
        return request;
    }

    @Override
    @Transactional
    public void approveLeave(Long requestId, Long teacherId, Integer status, String rejectReason) {
        LeaveRequest request = this.getById(requestId);
        if (request == null) {
            throw new RuntimeException("请假申请不存在");
        }
        
        request.setStatus(status);
        request.setApproveTime(LocalDateTime.now());
        if (status == 2 && rejectReason != null) {
            request.setApproveRemark(rejectReason);
        }
        this.updateById(request);
        
        // 如果批准，更新对应的签到记录状态为请假
        if (status == 1 && request.getTaskId() != null) {
            AttendanceRecord record = attendanceRecordMapper.selectOne(
                    new LambdaQueryWrapper<AttendanceRecord>()
                            .eq(AttendanceRecord::getTaskId, request.getTaskId())
                            .eq(AttendanceRecord::getStudentId, request.getStudentId()));
            if (record != null) {
                record.setStatus(4); // 请假
                attendanceRecordMapper.updateById(record);
            }
        }
        
        // 发送审批结果通知
        Course course = courseMapper.selectById(request.getCourseId());
        String statusText = status == 1 ? "已批准" : "已拒绝";
        String content = "您在课程【" + course.getName() + "】的请假申请" + statusText;
        if (status == 2 && rejectReason != null) {
            content += "，原因：" + rejectReason;
        }
        messageService.sendMessage(request.getStudentId(), 2, "请假审批结果", content);
    }

    @Override
    public IPage<LeaveRequest> pageStudentLeaves(Integer page, Integer size, Long studentId) {
        return this.page(new Page<>(page, size),
                new LambdaQueryWrapper<LeaveRequest>()
                        .eq(LeaveRequest::getStudentId, studentId)
                        .orderByDesc(LeaveRequest::getCreateTime));
    }

    @Override
    public IPage<LeaveRequest> pageTeacherLeaves(Integer page, Integer size, Long teacherId, Integer status) {
        // 先获取教师教授的课程
        LambdaQueryWrapper<Course> courseWrapper = new LambdaQueryWrapper<>();
        courseWrapper.eq(Course::getTeacherId, teacherId);
        var courses = courseMapper.selectList(courseWrapper);
        
        if (courses.isEmpty()) {
            return new Page<>(page, size);
        }
        
        var courseIds = courses.stream().map(Course::getId).toList();
        
        LambdaQueryWrapper<LeaveRequest> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(LeaveRequest::getCourseId, courseIds);
        if (status != null) {
            wrapper.eq(LeaveRequest::getStatus, status);
        }
        wrapper.orderByDesc(LeaveRequest::getCreateTime);
        
        return this.page(new Page<>(page, size), wrapper);
    }
}
