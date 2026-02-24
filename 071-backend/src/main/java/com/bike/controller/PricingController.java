package com.bike.controller;

import com.bike.common.Result;
import com.bike.entity.PricingRule;
import com.bike.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pricing")
public class PricingController {

    @Autowired
    private PricingService pricingService;

    @GetMapping("/list")
    public Result<?> getList() {
        return Result.success(pricingService.getList());
    }

    @PostMapping
    public Result<?> add(@RequestBody PricingRule rule) {
        pricingService.add(rule);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody PricingRule rule) {
        rule.setId(id);
        pricingService.update(rule);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        pricingService.delete(id);
        return Result.success();
    }
}
