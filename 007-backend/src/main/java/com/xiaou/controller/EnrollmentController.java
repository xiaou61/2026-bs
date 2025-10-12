package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Activity;
import com.xiaou.entity.Enrollment;
import com.xiaou.entity.User;
import com.xiaou.service.ActivityService;
import com.xiaou.service.EnrollmentService;
import com.xiaou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Result<?> enroll(@RequestBody Map<String, Long> params, @RequestAttribute Long userId) {
        Long activityId = params.get("activityId");
        
        Activity activity = activityService.getById(activityId);
        if (activity == null) {
            return Result.error("活动不存在");
        }

        if (activity.getStartTime().isBefore(LocalDateTime.now())) {
            return Result.error("活动已开始，无法报名");
        }

        if (activity.getCurrentParticipants() >= activity.getMaxParticipants()) {
            return Result.error("活动人数已满");
        }

        if (enrollmentService.hasEnrolled(activityId, userId)) {
            return Result.error("已报名该活动");
        }

        User user = userService.getById(userId);
        
        Enrollment enrollment = new Enrollment();
        enrollment.setActivityId(activityId);
        enrollment.setActivityTitle(activity.getTitle());
        enrollment.setUserId(userId);
        enrollment.setUserName(user.getName());
        enrollment.setPhone(user.getPhone());
        enrollment.setStatus(1);
        enrollment.setApplyTime(LocalDateTime.now());
        enrollment.setUpdateTime(LocalDateTime.now());
        
        enrollmentService.save(enrollment);
        activityService.incrementParticipants(activityId);
        
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> cancel(@PathVariable Long id, @RequestAttribute Long userId) {
        Enrollment enrollment = enrollmentService.getById(id);
        if (enrollment == null) {
            return Result.error("报名记录不存在");
        }

        if (!enrollment.getUserId().equals(userId)) {
            return Result.error("无权取消他人报名");
        }

        Activity activity = activityService.getById(enrollment.getActivityId());
        if (activity.getStartTime().isBefore(LocalDateTime.now())) {
            return Result.error("活动已开始，无法取消报名");
        }

        enrollment.setStatus(3);
        enrollment.setUpdateTime(LocalDateTime.now());
        enrollmentService.updateById(enrollment);
        
        activityService.decrementParticipants(enrollment.getActivityId());
        
        return Result.success();
    }

    @GetMapping
    public Result<?> list(@RequestParam(required = false) Long activityId) {
        QueryWrapper<Enrollment> wrapper = new QueryWrapper<>();
        if (activityId != null) {
            wrapper.eq("activity_id", activityId);
        }
        wrapper.orderByDesc("apply_time");
        
        List<Enrollment> list = enrollmentService.list(wrapper);
        return Result.success(list);
    }

    @GetMapping("/my")
    public Result<?> myEnrollments(@RequestAttribute Long userId) {
        List<Enrollment> list = enrollmentService.list(new QueryWrapper<Enrollment>()
                .eq("user_id", userId)
                .orderByDesc("apply_time"));
        return Result.success(list);
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        Enrollment enrollment = enrollmentService.getById(id);
        if (enrollment == null) {
            return Result.error("报名记录不存在");
        }

        Integer status = params.get("status");
        enrollment.setStatus(status);
        enrollment.setUpdateTime(LocalDateTime.now());
        enrollmentService.updateById(enrollment);

        if (status == 2) {
            activityService.decrementParticipants(enrollment.getActivityId());
        }

        return Result.success();
    }
}

