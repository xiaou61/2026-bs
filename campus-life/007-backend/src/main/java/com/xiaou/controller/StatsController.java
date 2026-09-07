package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Activity;
import com.xiaou.entity.Attendance;
import com.xiaou.entity.User;
import com.xiaou.service.ActivityService;
import com.xiaou.service.AttendanceService;
import com.xiaou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/overview")
    public Result<?> overview() {
        long totalUsers = userService.count(new QueryWrapper<User>().eq("role", "VOLUNTEER"));
        long totalActivities = activityService.count();
        
        List<User> users = userService.list();
        double totalHours = users.stream()
                .mapToDouble(User::getVolunteerHours)
                .sum();

        Map<String, Object> data = new HashMap<>();
        data.put("totalUsers", totalUsers);
        data.put("totalActivities", totalActivities);
        data.put("totalVolunteerHours", totalHours);

        return Result.success(data);
    }

    @GetMapping("/my")
    public Result<?> myStats(@RequestAttribute Long userId) {
        User user = userService.getById(userId);
        
        long participatedActivities = attendanceService.count(new QueryWrapper<Attendance>()
                .eq("user_id", userId)
                .eq("status", 1));

        Map<String, Object> data = new HashMap<>();
        data.put("totalPoints", user.getTotalPoints());
        data.put("availablePoints", user.getAvailablePoints());
        data.put("volunteerHours", user.getVolunteerHours());
        data.put("participatedActivities", participatedActivities);

        return Result.success(data);
    }

    @GetMapping("/activity-ranking")
    public Result<?> activityRanking() {
        List<Activity> activities = activityService.list(new QueryWrapper<Activity>()
                .eq("status", 3)
                .orderByDesc("current_participants")
                .last("limit 20"));

        List<Map<String, Object>> ranking = activities.stream().map(activity -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", activity.getId());
            map.put("title", activity.getTitle());
            map.put("participants", activity.getCurrentParticipants());
            return map;
        }).collect(Collectors.toList());

        return Result.success(ranking);
    }
}

