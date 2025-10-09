package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.LeaveRequest;
import com.xiaou.entity.User;
import com.xiaou.mapper.LeaveRequestMapper;
import com.xiaou.service.LeaveRequestService;
import com.xiaou.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 请假申请Service实现类
 * @author xiaou
 */
@Slf4j
@Service
public class LeaveRequestServiceImpl extends ServiceImpl<LeaveRequestMapper, LeaveRequest> implements LeaveRequestService {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void submitLeaveRequest(LeaveRequest leaveRequest) {
        // 计算请假天数
        long days = Duration.between(leaveRequest.getStartTime(), leaveRequest.getEndTime()).toDays();
        leaveRequest.setDays((int) days + 1);
        
        // 设置状态为待审批
        leaveRequest.setStatus(0);
        
        save(leaveRequest);
    }
    
    @Override
    public void approveLeaveRequest(Long id, Long approverId, Integer status, String comment) {
        LeaveRequest leaveRequest = getById(id);
        if (leaveRequest == null) {
            throw new BusinessException("请假申请不存在");
        }
        
        if (leaveRequest.getStatus() != 0) {
            throw new BusinessException("该申请已处理");
        }
        
        leaveRequest.setApproverId(approverId);
        leaveRequest.setStatus(status);
        leaveRequest.setApprovalComment(comment);
        leaveRequest.setApprovalTime(LocalDateTime.now());
        
        updateById(leaveRequest);
    }
    
    @Override
    public Page<LeaveRequest> getLeaveRequestPage(int pageNum, int pageSize, Long studentId, Integer status) {
        Page<LeaveRequest> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<LeaveRequest> wrapper = new LambdaQueryWrapper<>();
        
        if (studentId != null) {
            wrapper.eq(LeaveRequest::getStudentId, studentId);
        }
        
        if (status != null) {
            wrapper.eq(LeaveRequest::getStatus, status);
        }
        
        wrapper.orderByDesc(LeaveRequest::getCreateTime);
        Page<LeaveRequest> resultPage = page(page, wrapper);
        
        // 填充学生姓名和审批人姓名
        for (LeaveRequest record : resultPage.getRecords()) {
            User student = userService.getById(record.getStudentId());
            if (student != null) {
                record.setStudentName(student.getRealName() != null ? student.getRealName() : student.getUsername());
            }
            
            if (record.getApproverId() != null) {
                User approver = userService.getById(record.getApproverId());
                if (approver != null) {
                    record.setApproverName(approver.getRealName() != null ? approver.getRealName() : approver.getUsername());
                }
            }
        }
        
        return resultPage;
    }
    
    @Override
    public Object getLeaveStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 统计各状态的请假数量
        LambdaQueryWrapper<LeaveRequest> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeaveRequest::getStatus, 0);
        long pendingCount = count(wrapper);
        
        wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeaveRequest::getStatus, 1);
        long approvedCount = count(wrapper);
        
        wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeaveRequest::getStatus, 2);
        long rejectedCount = count(wrapper);
        
        statistics.put("pendingCount", pendingCount);
        statistics.put("approvedCount", approvedCount);
        statistics.put("rejectedCount", rejectedCount);
        statistics.put("totalCount", pendingCount + approvedCount + rejectedCount);
        
        return statistics;
    }
}

