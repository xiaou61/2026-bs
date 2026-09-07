package com.xiaou.herbal.controller;

import com.xiaou.herbal.common.Result;
import com.xiaou.herbal.entity.HealthProfile;
import com.xiaou.herbal.service.HealthProfileService;
import com.xiaou.herbal.util.UserContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health-profile")
@Validated
public class HealthProfileController {

    private final HealthProfileService healthProfileService;

    public HealthProfileController(HealthProfileService healthProfileService) {
        this.healthProfileService = healthProfileService;
    }

    @GetMapping("/info")
    public Result<HealthProfile> getProfile() {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            HealthProfile profile = healthProfileService.getUserProfile(userId);
            return Result.success(profile);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/save")
    public Result<Void> saveProfile(@RequestBody HealthProfile profile) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            healthProfileService.saveOrUpdateProfile(userId, profile);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
