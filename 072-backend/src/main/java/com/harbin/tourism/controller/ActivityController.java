package com.harbin.tourism.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.Activity;
import com.harbin.tourism.entity.ActivityRegistration;
import com.harbin.tourism.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/list")
    public Result<Page<Activity>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status) {
        return Result.success(activityService.page(pageNum, pageSize, keyword, status));
    }

    @GetMapping("/{id}")
    public Result<Activity> getById(@PathVariable Long id) {
        return Result.success(activityService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Activity activity) {
        activityService.save(activity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Activity activity) {
        activityService.update(activity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        activityService.delete(id);
        return Result.success();
    }

    @PostMapping("/{id}/register")
    public Result<Void> register(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        activityService.register(id, userId);
        return Result.success();
    }

    @PostMapping("/{id}/cancel")
    public Result<Void> cancelRegistration(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        activityService.cancelRegistration(id, userId);
        return Result.success();
    }

    @GetMapping("/registrations/my")
    public Result<List<ActivityRegistration>> myRegistrations(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(activityService.userRegistrations(userId));
    }

    @GetMapping("/{id}/registered")
    public Result<Boolean> isRegistered(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(activityService.isRegistered(id, userId));
    }
}
