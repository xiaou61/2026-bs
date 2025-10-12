package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.PointsRecord;
import com.xiaou.entity.User;
import com.xiaou.service.PointsRecordService;
import com.xiaou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/points")
public class PointsController {

    @Autowired
    private PointsRecordService pointsRecordService;

    @Autowired
    private UserService userService;

    @GetMapping("/records")
    public Result<?> records(@RequestParam(required = false) Long userId) {
        QueryWrapper<PointsRecord> wrapper = new QueryWrapper<>();
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        wrapper.orderByDesc("create_time");
        
        List<PointsRecord> list = pointsRecordService.list(wrapper);
        return Result.success(list);
    }

    @GetMapping("/my")
    public Result<?> myRecords(@RequestAttribute Long userId) {
        List<PointsRecord> list = pointsRecordService.getUserRecords(userId);
        return Result.success(list);
    }

    @GetMapping("/ranking")
    public Result<?> ranking() {
        List<User> users = userService.list(new QueryWrapper<User>()
                .eq("role", "VOLUNTEER")
                .eq("status", 1)
                .orderByDesc("total_points")
                .last("limit 50"));
        
        List<Map<String, Object>> ranking = users.stream().map(user -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("name", user.getName());
            map.put("totalPoints", user.getTotalPoints());
            map.put("volunteerHours", user.getVolunteerHours());
            return map;
        }).collect(Collectors.toList());
        
        return Result.success(ranking);
    }

    @PostMapping("/adjust")
    public Result<?> adjust(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Integer points = Integer.valueOf(params.get("points").toString());
        String remark = params.get("remark").toString();

        User user = userService.getById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        userService.updatePoints(userId, points);
        
        User updatedUser = userService.getById(userId);
        pointsRecordService.addRecord(userId, user.getName(), 3, points, 
                updatedUser.getAvailablePoints(), null, null, remark);

        return Result.success();
    }
}

