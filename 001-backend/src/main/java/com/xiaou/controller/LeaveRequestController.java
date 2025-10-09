package com.xiaou.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.LeaveRequest;
import com.xiaou.service.LeaveRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 请假申请控制器
 * @author xiaou
 */
@Slf4j
@RestController
@RequestMapping("/api/leave")
public class LeaveRequestController {
    
    @Autowired
    private LeaveRequestService leaveRequestService;
    
    /**
     * 提交请假申请
     */
    @PostMapping("/apply")
    public Result<Void> submitLeaveRequest(@RequestBody LeaveRequest leaveRequest, 
                                           HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 只有学生可以提交请假申请
        if (!"student".equals(role)) {
            return Result.error(403, "只有学生可以提交请假申请");
        }
        
        leaveRequest.setStudentId(userId);
        leaveRequestService.submitLeaveRequest(leaveRequest);
        return Result.success();
    }
    
    /**
     * 分页查询请假申请列表
     */
    @GetMapping("/list")
    public Result<Page<LeaveRequest>> getLeaveRequestList(@RequestParam(defaultValue = "1") int pageNum,
                                                          @RequestParam(defaultValue = "10") int pageSize,
                                                          @RequestParam(required = false) Long studentId,
                                                          @RequestParam(required = false) Integer status,
                                                          HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 学生只能查看自己的请假申请
        if ("student".equals(role)) {
            studentId = currentUserId;
        }
        
        Page<LeaveRequest> page = leaveRequestService.getLeaveRequestPage(pageNum, pageSize, studentId, status);
        return Result.success(page);
    }
    
    /**
     * 审批请假申请
     */
    @PostMapping("/approve")
    public Result<Void> approveLeaveRequest(@RequestBody ApprovalRequest request, 
                                            HttpServletRequest httpRequest) {
        Long approverId = (Long) httpRequest.getAttribute("userId");
        String role = (String) httpRequest.getAttribute("role");
        
        // 只有教师和管理员可以审批
        if (!"teacher".equals(role) && !"admin".equals(role)) {
            return Result.error(403, "权限不足");
        }
        
        leaveRequestService.approveLeaveRequest(request.getId(), approverId, 
                                                request.getStatus(), request.getComment());
        return Result.success();
    }
    
    /**
     * 获取请假统计数据
     */
    @GetMapping("/statistics")
    public Result<Object> getLeaveStatistics(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        
        // 只有管理员可以查看统计数据
        if (!"admin".equals(role)) {
            return Result.error(403, "权限不足");
        }
        
        Object statistics = leaveRequestService.getLeaveStatistics();
        return Result.success(statistics);
    }
    
    /**
     * 审批请求实体
     */
    public static class ApprovalRequest {
        private Long id;
        private Integer status;
        private String comment;
        
        // getter和setter
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Integer getStatus() { return status; }
        public void setStatus(Integer status) { this.status = status; }
        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
    }
}
