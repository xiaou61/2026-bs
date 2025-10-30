package com.xiaou.sport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.HealthProfile;
import com.xiaou.sport.service.HealthProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/api/health")
public class HealthProfileController {

    @Autowired
    private HealthProfileService healthProfileService;

    @PostMapping("/record")
    public Result<Void> createRecord(@RequestAttribute Long userId, @RequestBody HealthProfile profile) {
        profile.setUserId(userId);

        if (profile.getWeight() != null) {
            LambdaQueryWrapper<HealthProfile> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(HealthProfile::getUserId, userId)
                    .orderByDesc(HealthProfile::getRecordDate)
                    .last("LIMIT 1");
            HealthProfile lastProfile = healthProfileService.getOne(wrapper);

            if (lastProfile != null && lastProfile.getWeight() != null) {
                BigDecimal heightInMeters = lastProfile.getWeight().divide(new BigDecimal(100), 2,
                        RoundingMode.HALF_UP);
                BigDecimal bmi = profile.getWeight().divide(heightInMeters.multiply(heightInMeters), 2,
                        RoundingMode.HALF_UP);
                profile.setBmi(bmi);
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
