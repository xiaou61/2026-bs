package com.xiaou.sport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.TeamActivity;
import com.xiaou.sport.service.TeamActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activity")
public class TeamActivityController {

    @Autowired
    private TeamActivityService teamActivityService;

    @PostMapping("/create")
    public Result<Void> createActivity(@RequestAttribute Long userId, @RequestBody TeamActivity activity) {
        activity.setCreatorId(userId);
        activity.setCurrentParticipants(1);
        activity.setStatus("recruiting");
        teamActivityService.save(activity);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Page<TeamActivity>> listActivities(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String sportType) {
        Page<TeamActivity> pageInfo = new Page<>(page, size);
        LambdaQueryWrapper<TeamActivity> wrapper = new LambdaQueryWrapper<>();
        if (sportType != null && !sportType.isEmpty()) {
            wrapper.eq(TeamActivity::getSportType, sportType);
        }
        wrapper.orderByDesc(TeamActivity::getActivityTime);
        teamActivityService.page(pageInfo, wrapper);
        return Result.success(pageInfo);
    }

    @GetMapping("/{id}")
    public Result<TeamActivity> getActivity(@PathVariable Long id) {
        TeamActivity activity = teamActivityService.getById(id);
        return Result.success(activity);
    }

    @PostMapping("/{id}/join")
    public Result<Void> joinActivity(@PathVariable Long id, @RequestAttribute Long userId) {
        boolean success = teamActivityService.joinActivity(id, userId);
        if (success) {
            return Result.success();
        }
        return Result.error("加入失败");
    }

    @PostMapping("/{id}/cancel")
    public Result<Void> cancelJoin(@PathVariable Long id, @RequestAttribute Long userId) {
        boolean success = teamActivityService.cancelJoin(id, userId);
        if (success) {
            return Result.success();
        }
        return Result.error("取消失败");
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody TeamActivity activity) {
        TeamActivity existActivity = teamActivityService.getById(id);
        existActivity.setStatus(activity.getStatus());
        teamActivityService.updateById(existActivity);
        return Result.success();
    }
}
