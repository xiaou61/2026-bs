package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.entity.Merchant;
import com.groupbuy.service.MerchantService;
import com.groupbuy.service.StatsService;
import com.groupbuy.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private MerchantService merchantService;

    @GetMapping("/overview")
    public Result<?> overview(HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        return Result.success(statsService.overview());
    }

    @GetMapping("/sales")
    public Result<?> sales(HttpServletRequest request, @RequestParam(defaultValue = "7") Integer days) {
        AuthUtils.requireAdmin(request);
        return Result.success(statsService.sales(days));
    }

    @GetMapping("/hot-products")
    public Result<?> hotProducts(HttpServletRequest request, @RequestParam(defaultValue = "10") Integer limit) {
        AuthUtils.requireAdmin(request);
        return Result.success(statsService.hotProducts(limit));
    }

    @GetMapping("/merchant")
    public Result<?> merchantStats(HttpServletRequest request,
                                   @RequestParam(defaultValue = "7") Integer days) {
        AuthUtils.requireMerchant(request);
        Merchant merchant = merchantService.requireApprovedMerchant(AuthUtils.getUserId(request));
        return Result.success(statsService.merchantStats(merchant.getId(), days));
    }
}
