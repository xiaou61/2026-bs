package com.xiaou.sport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.HealthProfile;
import com.xiaou.sport.service.HealthProfileService;
import com.xiaou.sport.service.UserService;
import com.xiaou.sport.utils.HealthMetricUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/health")
public class HealthProfileController {

    @Autowired
    private HealthProfileService healthProfileService;

    @Autowired
    private UserService userService;

    @PostMapping("/record")
    public Result<Void> createRecord(@RequestAttribute Long userId, @RequestBody HealthProfile profile) {
        profile.setUserId(userId);
        if (profile.getRecordDate() == null) {
            profile.setRecordDate(LocalDate.now());
        }

        if (profile.getWeight() != null) {
            var user = userService.getById(userId);
            if (user != null) {
                profile.setBmi(HealthMetricUtil.calculateBmi(profile.getWeight(), user.getHeight()));
            }
        }

        healthProfileService.save(profile);
        return Result.success();
    }

    @GetMapping("/records")
    public Result<Page<HealthProfile>> getRecords(@RequestAttribute Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<HealthProfile> pageInfo = new Page<>(page, size);
        LambdaQueryWrapper<HealthProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthProfile::getUserId, userId)
                .orderByDesc(HealthProfile::getRecordDate);
        healthProfileService.page(pageInfo, wrapper);
        return Result.success(pageInfo);
    }

    @GetMapping("/trend")
    public Result<List<HealthProfile>> getTrend(@RequestAttribute Long userId,
            @RequestParam(defaultValue = "30") Integer days) {
        LambdaQueryWrapper<HealthProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthProfile::getUserId, userId)
                .orderByDesc(HealthProfile::getRecordDate)
                .last("LIMIT " + days);
        List<HealthProfile> profiles = healthProfileService.list(wrapper);
        return Result.success(profiles);
    }
}
