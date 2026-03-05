package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.Activity;
import com.alumni.entity.ActivityPhoto;
import com.alumni.entity.ActivitySign;
import com.alumni.service.ActivityService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/list")
    public Result<Page<Activity>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       String title, Integer status) {
        return Result.success(activityService.list(pageNum, pageSize, title, status));
    }

    @GetMapping("/{id}")
    public Result<Activity> getById(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(activityService.getById(id, userId));
    }

    @PostMapping
    public Result<?> add(@RequestBody Activity activity, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        activity.setOrganizerId(userId);
        activityService.add(activity);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Activity activity) {
        activityService.update(activity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        activityService.delete(id);
        return Result.success();
    }

    @PostMapping("/{id}/sign")
    public Result<?> sign(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        activityService.sign(id, userId);
        return Result.success();
    }

    @DeleteMapping("/{id}/sign")
    public Result<?> cancelSign(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        activityService.cancelSign(id, userId);
        return Result.success();
    }

    @PostMapping("/{id}/check")
    public Result<?> check(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        activityService.check(id, userId);
        return Result.success();
    }

    @GetMapping("/{id}/signs")
    public Result<List<ActivitySign>> getSignList(@PathVariable Long id) {
        return Result.success(activityService.getSignList(id));
    }

    @GetMapping("/{id}/photos")
    public Result<List<ActivityPhoto>> getPhotos(@PathVariable Long id) {
        return Result.success(activityService.getPhotos(id));
    }

    @PostMapping("/{id}/photo")
    public Result<?> addPhoto(@PathVariable Long id, @RequestBody ActivityPhoto photo, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        photo.setActivityId(id);
        photo.setUploadUserId(userId);
        activityService.addPhoto(photo);
        return Result.success();
    }

    @DeleteMapping("/photo/{id}")
    public Result<?> deletePhoto(@PathVariable Long id) {
        activityService.deletePhoto(id);
        return Result.success();
    }
}
