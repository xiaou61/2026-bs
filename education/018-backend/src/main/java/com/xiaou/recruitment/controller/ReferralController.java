package com.xiaou.recruitment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.recruitment.common.Result;
import com.xiaou.recruitment.entity.Referral;
import com.xiaou.recruitment.service.ReferralService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/referral")
public class ReferralController {

    @Autowired
    private ReferralService referralService;

    @PostMapping("/publish")
    public Result<?> publishReferral(@RequestBody Referral referral, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        referral.setUserId(userId);
        if (referralService.publishReferral(referral)) {
            return Result.success(referral);
        }
        return Result.error("发布失败");
    }

    @GetMapping("/list")
    public Result<?> getReferralList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        IPage<Referral> referralPage = referralService.getReferralList(page, size, keyword);
        return Result.success(referralPage);
    }

    @GetMapping("/{id}")
    public Result<?> getReferralById(@PathVariable Long id) {
        Referral referral = referralService.getReferralById(id);
        if (referral != null) {
            return Result.success(referral);
        }
        return Result.error("内推不存在");
    }

    @PutMapping("/close/{id}")
    public Result<?> closeReferral(@PathVariable Long id, HttpServletRequest request) {
        if (referralService.closeReferral(id)) {
            return Result.success();
        }
        return Result.error("关闭失败");
    }
}
