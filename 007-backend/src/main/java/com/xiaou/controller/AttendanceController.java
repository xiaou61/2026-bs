package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Activity;
import com.xiaou.entity.Attendance;
import com.xiaou.entity.User;
import com.xiaou.service.ActivityService;
import com.xiaou.service.AttendanceService;
import com.xiaou.service.EnrollmentService;
import com.xiaou.service.PointsRecordService;
import com.xiaou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private PointsRecordService pointsRecordService;

    @PostMapping("/check-in")
    public Result<?> checkIn(@RequestBody Map<String, Long> params, @RequestAttribute Long userId) {
        Long activityId = params.get("activityId");
        
        Activity activity = activityService.getById(activityId);
        if (activity == null) {
            return Result.error("活动不存在");
        }

        if (activity.getStartTime().isAfter(LocalDateTime.now())) {
            return Result.error("活动未开始");
        }

        if (!enrollmentService.hasEnrolled(activityId, userId)) {
            return Result.error("未报名该活动");
        }

        if (attendanceService.hasCheckedIn(activityId, userId)) {
            return Result.error("已签到");
        }

        User user = userService.getById(userId);
        
        Attendance attendance = new Attendance();
        attendance.setActivityId(activityId);
        attendance.setActivityTitle(activity.getTitle());
        attendance.setUserId(userId);
        attendance.setUserName(user.getName());
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setStatus(0);
        attendance.setCreateTime(LocalDateTime.now());
        attendance.setUpdateTime(LocalDateTime.now());
        
        attendanceService.save(attendance);
        return Result.success();
    }

    @PutMapping("/{id}/check-out")
    public Result<?> checkOut(@PathVariable Long id, @RequestAttribute Long userId) {
        Attendance attendance = attendanceService.getById(id);
        if (attendance == null) {
            return Result.error("签到记录不存在");
        }

        if (!attendance.getUserId().equals(userId)) {
            return Result.error("无权签退");
        }

        if (attendance.getStatus() == 1) {
            return Result.error("已签退");
        }

        Activity activity = activityService.getById(attendance.getActivityId());
        LocalDateTime checkOutTime = LocalDateTime.now();
        
        long minutes = Duration.between(attendance.getCheckInTime(), checkOutTime).toMinutes();
        double actualHours = minutes / 60.0;
        
        double ratio = actualHours / activity.getHours();
        if (ratio > 1) {
            ratio = 1.0;
        }
        
        int earnedPoints = (int) (activity.getPoints() * ratio);
        
        attendance.setCheckOutTime(checkOutTime);
        attendance.setActualHours(actualHours);
        attendance.setPoints(earnedPoints);
        attendance.setStatus(1);
        attendance.setUpdateTime(LocalDateTime.now());
        attendanceService.updateById(attendance);
        
        userService.updatePoints(userId, earnedPoints);
        userService.updateVolunteerHours(userId, actualHours);
        
        User user = userService.getById(userId);
        pointsRecordService.addRecord(userId, user.getName(), 1, earnedPoints, 
                user.getAvailablePoints(), activity.getId(), activity.getTitle(), 
                "参与活动获得积分");
        
        return Result.success();
    }

    @GetMapping
    public Result<?> list(@RequestParam(required = false) Long activityId,
                         @RequestParam(required = false) Long userId) {
        QueryWrapper<Attendance> wrapper = new QueryWrapper<>();
        if (activityId != null) {
            wrapper.eq("activity_id", activityId);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        wrapper.orderByDesc("create_time");
        
        List<Attendance> list = attendanceService.list(wrapper);
        return Result.success(list);
    }

    @GetMapping("/my")
    public Result<?> myAttendances(@RequestAttribute Long userId) {
        List<Attendance> list = attendanceService.list(new QueryWrapper<Attendance>()
                .eq("user_id", userId)
                .orderByDesc("create_time"));
        return Result.success(list);
    }

    @PostMapping("/manual")
    public Result<?> manualCheckIn(@RequestBody Attendance attendance) {
        Activity activity = activityService.getById(attendance.getActivityId());
        if (activity == null) {
            return Result.error("活动不存在");
        }

        User user = userService.getById(attendance.getUserId());
        if (user == null) {
            return Result.error("用户不存在");
        }

        attendance.setActivityTitle(activity.getTitle());
        attendance.setUserName(user.getName());
        attendance.setStatus(1);
        attendance.setCreateTime(LocalDateTime.now());
        attendance.setUpdateTime(LocalDateTime.now());
        attendanceService.save(attendance);

        userService.updatePoints(user.getId(), attendance.getPoints());
        userService.updateVolunteerHours(user.getId(), attendance.getActualHours());
        
        User updatedUser = userService.getById(user.getId());
        pointsRecordService.addRecord(user.getId(), user.getName(), 1, attendance.getPoints(), 
                updatedUser.getAvailablePoints(), activity.getId(), activity.getTitle(), 
                "管理员补签");

        return Result.success();
    }
}

