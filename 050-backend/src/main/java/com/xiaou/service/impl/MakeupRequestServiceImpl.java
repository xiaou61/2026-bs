package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.AttendanceRecord;
import com.xiaou.entity.AttendanceTask;
import com.xiaou.entity.Course;
import com.xiaou.entity.MakeupRequest;
import com.xiaou.mapper.AttendanceRecordMapper;
import com.xiaou.mapper.AttendanceTaskMapper;
import com.xiaou.mapper.CourseMapper;
import com.xiaou.mapper.MakeupRequestMapper;
import com.xiaou.service.AttendanceStatService;
import com.xiaou.service.MakeupRequestService;
import com.xiaou.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MakeupRequestServiceImpl extends ServiceImpl<MakeupRequestMapper, MakeupRequest> implements MakeupRequestService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private AttendanceTaskMapper attendanceTaskMapper;

    @Autowired
    private AttendanceRecordMapper attendanceRecordMapper;

    @Autowired
    private AttendanceStatService attendanceStatService;

    @Autowired
    private MessageService messageService;

    @Override
    public MakeupRequest submitMakeup(MakeupRequest request) {
        request.setStatus(0); // 待审核
        request.setCreateTime(LocalDateTime.now());
        this.save(request);
        return request;
    }

    @Override
    @Transactional
    public void approveMakeup(Long requestId, Long teacherId, Integer status, String rejectReason) {
        MakeupRequest request = this.getById(requestId);
        if (request == null) {
            throw new RuntimeException("补签申请不存在");
        }
        
        request.setStatus(status);
        request.setApproveTime(LocalDateTime.now());
        if (status == 2 && rejectReason != null) {
            request.setApproveRemark(rejectReason);
        }
        this.updateById(request);
        
        // 如果批准，更新对应的签到记录状态为补签
        if (status == 1) {
            AttendanceRecord record = attendanceRecordMapper.selectOne(
                    new LambdaQueryWrapper<AttendanceRecord>()
                            .eq(AttendanceRecord::getTaskId, request.getTaskId())
                            .eq(AttendanceRecord::getStudentId, request.getStudentId()));
            if (record != null) {
                record.setStatus(5); // 补签
                record.setSignTime(LocalDateTime.now());
                attendanceRecordMapper.updateById(record);
                
                // 更新考勤统计
                AttendanceTask task = attendanceTaskMapper.selectById(request.getTaskId());
                if (task != null) {
                    attendanceStatService.refreshStat(request.getStudentId(), task.getCourseId());
                }
            }
        }
        
        // 发送审批结果通知
        AttendanceTask task = attendanceTaskMapper.selectById(request.getTaskId());
        Course course = courseMapper.selectById(task.getCourseId());
        String statusText = status == 1 ? "已批准" : "已拒绝";
        String content = "您在课程【" + course.getName() + "】的补签申请" + statusText;
        if (status == 2 && rejectReason != null) {
            content += "，原因：" + rejectReason;
        }
        messageService.sendMessage(request.getStudentId(), 2, "补签审批结果", content);
    }

    @Override
    public IPage<MakeupRequest> pageStudentMakeups(Integer page, Integer size, Long studentId) {
        return this.page(new Page<>(page, size),
                new LambdaQueryWrapper<MakeupRequest>()
                        .eq(MakeupRequest::getStudentId, studentId)
                        .orderByDesc(MakeupRequest::getCreateTime));
    }

    @Override
    public IPage<MakeupRequest> pageTeacherMakeups(Integer page, Integer size, Long teacherId, Integer status) {
        // 先获取教师教授的课程的签到任务
        LambdaQueryWrapper<Course> courseWrapper = new LambdaQueryWrapper<>();
        courseWrapper.eq(Course::getTeacherId, teacherId);
        var courses = courseMapper.selectList(courseWrapper);
        
        if (courses.isEmpty()) {
            return new Page<>(page, size);
        }
        
        var courseIds = courses.stream().map(Course::getId).toList();
        
        // 获取这些课程的签到任务
        var tasks = attendanceTaskMapper.selectList(
                new LambdaQueryWrapper<AttendanceTask>()
                        .in(AttendanceTask::getCourseId, courseIds));
        
        if (tasks.isEmpty()) {
            return new Page<>(page, size);
        }
        
        var taskIds = tasks.stream().map(AttendanceTask::getId).toList();
        
        LambdaQueryWrapper<MakeupRequest> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(MakeupRequest::getTaskId, taskIds);
        if (status != null) {
            wrapper.eq(MakeupRequest::getStatus, status);
        }
        wrapper.orderByDesc(MakeupRequest::getCreateTime);
        
        return this.page(new Page<>(page, size), wrapper);
    }
}
