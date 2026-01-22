package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.LeaveRequest;

public interface LeaveRequestService extends IService<LeaveRequest> {
    LeaveRequest submitLeave(LeaveRequest request);
    void approveLeave(Long requestId, Long teacherId, Integer status, String rejectReason);
    IPage<LeaveRequest> pageStudentLeaves(Integer page, Integer size, Long studentId);
    IPage<LeaveRequest> pageTeacherLeaves(Integer page, Integer size, Long teacherId, Integer status);
}
