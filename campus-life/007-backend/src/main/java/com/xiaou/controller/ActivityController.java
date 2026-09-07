package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public Result<?> list(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer size,
                         @RequestParam(required = false) Integer status,
                         @RequestParam(required = false) String title) {
        IPage<Activity> result = activityService.getActivityList(page, size, status, title);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        Activity activity = activityService.getById(id);
        return Result.success(activity);
    }

    @PostMapping
    public Result<?> create(@RequestBody Activity activity, @RequestAttribute Long userId) {
        User user = userService.getById(userId);
        
        activity.setPublisherId(userId);
        activity.setPublisherName(user.getName());
        activity.setCurrentParticipants(0);
        activity.setStatus(1);
        activity.setCreateTime(LocalDateTime.now());
        activity.setUpdateTime(LocalDateTime.now());
        
        activityService.save(activity);
        return Result.success(activity);
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Activity activity) {
        activity.setId(id);
        activity.setUpdateTime(LocalDateTime.now());
        activityService.updateById(activity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        activityService.removeById(id);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        Activity activity = activityService.getById(id);
        if (activity == null) {
            return Result.error("活动不存在");
        }
        
        activity.setStatus(params.get("status"));
        activity.setUpdateTime(LocalDateTime.now());
        activityService.updateById(activity);
        return Result.success();
    }

    @GetMapping("/my/published")
    public Result<?> myPublished(@RequestAttribute Long userId) {
        List<Activity> list = activityService.list(new QueryWrapper<Activity>()
                .eq("publisher_id", userId)
                .orderByDesc("create_time"));
        return Result.success(list);
    }

    @GetMapping("/my/enrolled")
    public Result<?> myEnrolled(@RequestAttribute Long userId) {
        List<Enrollment> enrollments = enrollmentService.list(new QueryWrapper<Enrollment>()
                .eq("user_id", userId)
                .in("status", 0, 1)
                .orderByDesc("apply_time"));
        return Result.success(enrollments);
    }
}

