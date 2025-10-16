package com.xiaou.campusclub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.campusclub.common.Result;
import com.xiaou.campusclub.dto.ActivityRequest;
import com.xiaou.campusclub.service.ActivityService;
import com.xiaou.campusclub.vo.ActivityVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    
    @Autowired
    private ActivityService activityService;
    
    @GetMapping
    public Result<IPage<ActivityVO>> getActivityList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(activityService.getActivityList(page, size, status, userId));
    }
    
    @GetMapping("/{id}")
    public Result<ActivityVO> getActivityDetail(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(activityService.getActivityDetail(id, userId));
    }
    
    @PostMapping
    public Result<Long> createActivity(HttpServletRequest request, @RequestBody ActivityRequest activityRequest) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(activityService.createActivity(userId, activityRequest));
    }
    
    @PutMapping("/{id}")
    public Result<Void> updateActivity(@PathVariable Long id, HttpServletRequest request, 
                                      @RequestBody ActivityRequest activityRequest) {
        Long userId = (Long) request.getAttribute("userId");
        activityService.updateActivity(id, userId, activityRequest);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> cancelActivity(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        activityService.cancelActivity(id, userId);
        return Result.success();
    }
    
    @PostMapping("/{id}/register")
    public Result<Void> registerActivity(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        activityService.registerActivity(id, userId);
        return Result.success();
    }
    
    @DeleteMapping("/{id}/register")
    public Result<Void> cancelRegistration(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        activityService.cancelRegistration(id, userId);
        return Result.success();
    }
    
    @PostMapping("/{id}/sign")
    public Result<Void> signActivity(@PathVariable Long id, HttpServletRequest request, 
                                    @RequestParam String signCode) {
        Long userId = (Long) request.getAttribute("userId");
        activityService.signActivity(id, userId, signCode);
        return Result.success();
    }
    
    @PostMapping("/{id}/rate")
    public Result<Void> rateActivity(@PathVariable Long id, HttpServletRequest request,
                                    @RequestParam Integer rating,
                                    @RequestParam(required = false) String comment) {
        Long userId = (Long) request.getAttribute("userId");
        activityService.rateActivity(id, userId, rating, comment);
        return Result.success();
    }
    
    @GetMapping("/my")
    public Result<List<ActivityVO>> getMyActivities(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(activityService.getMyActivities(userId));
    }
}

