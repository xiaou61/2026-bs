package com.xiaou.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.Activity;
import com.xiaou.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 活动管理控制器
 * @author xiaou
 */
@Slf4j
@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    
    @Autowired
    private ActivityService activityService;
    
    /**
     * 发布活动
     */
    @PostMapping("/add")
    public Result<Void> publishActivity(@RequestBody Activity activity, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 只有教师和管理员可以发布活动
        if (!"teacher".equals(role) && !"admin".equals(role)) {
            return Result.error(403, "权限不足");
        }
        
        activity.setPublisherId(userId);
        activityService.publishActivity(activity);
        return Result.success();
    }
    
    /**
     * 分页查询活动列表
     */
    @GetMapping("/list")
    public Result<Page<Activity>> getActivityList(@RequestParam(defaultValue = "1") int pageNum,
                                                  @RequestParam(defaultValue = "10") int pageSize,
                                                  @RequestParam(required = false) Integer status) {
        Page<Activity> page = activityService.getActivityPage(pageNum, pageSize, status);
        return Result.success(page);
    }
    
    /**
     * 根据ID查询活动详情
     */
    @GetMapping("/{id}")
    public Result<Activity> getActivityById(@PathVariable Long id, HttpServletRequest request) {
        Activity activity = activityService.getById(id);
        
        if (activity != null) {
            Long userId = (Long) request.getAttribute("userId");
            String role = (String) request.getAttribute("role");
            
            // 如果是学生，检查是否已报名
            if ("student".equals(role)) {
                boolean hasSignedUp = activityService.hasSignedUp(id, userId);
                // 可以通过额外字段返回报名状态，这里简化处理
            }
        }
        
        return Result.success(activity);
    }
    
    /**
     * 学生报名活动
     */
    @PostMapping("/signup")
    public Result<Void> signupActivity(@RequestBody SignupRequest request, HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        String role = (String) httpRequest.getAttribute("role");
        
        // 只有学生可以报名活动
        if (!"student".equals(role)) {
            return Result.error(403, "只有学生可以报名活动");
        }
        
        activityService.signupActivity(request.getActivityId(), userId, request.getRemark());
        return Result.success();
    }
    
    /**
     * 取消报名
     */
    @PostMapping("/cancel-signup")
    public Result<Void> cancelSignup(@RequestBody CancelSignupRequest request, HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        String role = (String) httpRequest.getAttribute("role");
        
        // 只有学生可以取消报名
        if (!"student".equals(role)) {
            return Result.error(403, "只有学生可以取消报名");
        }
        
        activityService.cancelSignup(request.getActivityId(), userId);
        return Result.success();
    }
    
    /**
     * 获取活动报名列表
     */
    @GetMapping("/{id}/signups")
    public Result<Object> getActivitySignupList(@PathVariable Long id,
                                                @RequestParam(defaultValue = "1") int pageNum,
                                                @RequestParam(defaultValue = "10") int pageSize,
                                                HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 检查权限：发布者或管理员可以查看报名列表
        Activity activity = activityService.getById(id);
        if (activity == null) {
            return Result.error("活动不存在");
        }
        
        if (!"admin".equals(role) && !currentUserId.equals(activity.getPublisherId())) {
            return Result.error(403, "权限不足");
        }
        
        Object signupList = activityService.getActivitySignupList(id, pageNum, pageSize);
        return Result.success(signupList);
    }
    
    /**
     * 更新活动
     */
    @PutMapping("/update")
    public Result<Void> updateActivity(@RequestBody Activity activity, HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 检查权限：只有发布者本人或管理员可以修改
        Activity existActivity = activityService.getById(activity.getId());
        if (existActivity == null) {
            return Result.error("活动不存在");
        }
        
        if (!"admin".equals(role) && !currentUserId.equals(existActivity.getPublisherId())) {
            return Result.error(403, "权限不足");
        }
        
        activityService.updateById(activity);
        return Result.success();
    }
    
    /**
     * 删除活动
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteActivity(@PathVariable Long id, HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        // 检查权限：只有发布者本人或管理员可以删除
        Activity existActivity = activityService.getById(id);
        if (existActivity == null) {
            return Result.error("活动不存在");
        }
        
        if (!"admin".equals(role) && !currentUserId.equals(existActivity.getPublisherId())) {
            return Result.error(403, "权限不足");
        }
        
        activityService.removeById(id);
        return Result.success();
    }
    
    /**
     * 报名请求实体
     */
    public static class SignupRequest {
        private Long activityId;
        private String remark;
        
        public Long getActivityId() { return activityId; }
        public void setActivityId(Long activityId) { this.activityId = activityId; }
        public String getRemark() { return remark; }
        public void setRemark(String remark) { this.remark = remark; }
    }
    
    /**
     * 取消报名请求实体
     */
    public static class CancelSignupRequest {
        private Long activityId;
        
        public Long getActivityId() { return activityId; }
        public void setActivityId(Long activityId) { this.activityId = activityId; }
    }
}
