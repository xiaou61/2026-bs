package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.entity.Merchant;
import com.groupbuy.service.MerchantService;
import com.groupbuy.service.StatsService;
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
    public Result<?> overview() {
        return Result.success(statsService.overview());
    }

    @GetMapping("/sales")
    public Result<?> sales(@RequestParam(defaultValue = "7") Integer days) {
        return Result.success(statsService.sales(days));
    }

    @GetMapping("/hot-products")
    public Result<?> hotProducts(@RequestParam(defaultValue = "10") Integer limit) {
        return Result.success(statsService.hotProducts(limit));
    }

    @GetMapping("/merchant")
    public Result<?> merchantStats(HttpServletRequest request,
                                   @RequestParam(defaultValue = "7") Integer days) {
        Long userId = (Long) request.getAttribute("userId");
        Merchant merchant = merchantService.getByUserId(userId);
        if (merchant == null) {
            return Result.error("商家信息不存在");
        }
        return Result.success(statsService.merchantStats(merchant.getId(), days));
    }
}
