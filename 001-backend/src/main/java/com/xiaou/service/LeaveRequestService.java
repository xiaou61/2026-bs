package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.LeaveRequest;

/**
 * 请假申请Service接口
 * @author xiaou
 */
public interface LeaveRequestService extends IService<LeaveRequest> {
    
    /**
     * 学生提交请假申请
     */
    void submitLeaveRequest(LeaveRequest leaveRequest);
    
    /**
     * 审批请假申请
     */
    void approveLeaveRequest(Long id, Long approverId, Integer status, String comment);
    
    /**
     * 分页查询请假申请
     */
    Page<LeaveRequest> getLeaveRequestPage(int pageNum, int pageSize, Long studentId, Integer status);
    
    /**
     * 获取请假统计数据
     */
    Object getLeaveStatistics();
}

