package com.xiaou.herbal.controller;

import com.xiaou.herbal.common.Result;
import com.xiaou.herbal.entity.CreatorAuth;
import com.xiaou.herbal.service.CreatorAuthService;
import com.xiaou.herbal.util.UserContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/creator/auth")
@Validated
public class CreatorAuthController {

    private final CreatorAuthService creatorAuthService;

    public CreatorAuthController(CreatorAuthService creatorAuthService) {
        this.creatorAuthService = creatorAuthService;
    }

    @PostMapping("/apply")
    public Result<Void> applyAuth(
            @RequestParam String authType,
            @RequestParam String credentials) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            creatorAuthService.applyAuth(userId, authType, credentials);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/info")
    public Result<CreatorAuth> getAuthInfo() {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            CreatorAuth auth = creatorAuthService.getUserAuth(userId);
            return Result.success(auth);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
