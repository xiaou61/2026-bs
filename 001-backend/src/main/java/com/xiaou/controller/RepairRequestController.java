package com.xiaou.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.RepairRequest;
import com.xiaou.service.RepairRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 报修申请控制器
 * @author xiaou
 */
@Slf4j
@RestController
@RequestMapping("/api/repair")
public class RepairRequestController {
    
    @Autowired
    private RepairRequestService repairRequestService;
    
    /**
     * 提交报修申请
     */
    @PostMapping("/submit")
    public Result<Void> submitRepairRequest(@RequestBody RepairRequest repairRequest, 
                                            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 只有学生可以提交报修申请
        if (!"student".equals(role)) {
            return Result.error(403, "只有学生可以提交报修申请");
        }
        
        repairRequest.setStudentId(userId);
        repairRequestService.submitRepairRequest(repairRequest);
        return Result.success();
    }
    
    /**
     * 分页查询报修申请列表
     */
    @GetMapping("/list")
    public Result<Page<RepairRequest>> getRepairRequestList(@RequestParam(defaultValue = "1") int pageNum,
                                                            @RequestParam(defaultValue = "10") int pageSize,
                                                            @RequestParam(required = false) Long studentId,
                                                            @RequestParam(required = false) Integer status,
                                                            HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 学生只能查看自己的报修申请
        if ("student".equals(role)) {
            studentId = currentUserId;
        }
        
        Page<RepairRequest> page = repairRequestService.getRepairRequestPage(pageNum, pageSize, studentId, status);
        return Result.success(page);
    }
    
    /**
     * 处理报修申请
     */
    @PostMapping("/handle")
    public Result<Void> handleRepairRequest(@RequestBody HandleRequest request, 
                                            HttpServletRequest httpRequest) {
        Long handlerId = (Long) httpRequest.getAttribute("userId");
        String role = (String) httpRequest.getAttribute("role");
        
        // 只有管理员可以处理报修
        if (!"admin".equals(role)) {
            return Result.error(403, "权限不足");
        }
        
        repairRequestService.handleRepairRequest(request.getId(), handlerId, 
                                                 request.getStatus(), request.getComment());
        return Result.success();
    }
    
    /**
     * 获取报修统计数据
     */
    @GetMapping("/statistics")
    public Result<Object> getRepairStatistics(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        
        // 只有管理员可以查看统计数据
        if (!"admin".equals(role)) {
            return Result.error(403, "权限不足");
        }
        
        Object statistics = repairRequestService.getRepairStatistics();
        return Result.success(statistics);
    }
    
    /**
     * 处理请求实体
     */
    public static class HandleRequest {
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
