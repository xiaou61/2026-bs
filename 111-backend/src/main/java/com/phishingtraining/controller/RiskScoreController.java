package com.phishingtraining.controller;

import com.phishingtraining.common.Result;
import com.phishingtraining.entity.RiskScore;
import com.phishingtraining.service.RiskScoreService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/risk-score")
@RequiredArgsConstructor
public class RiskScoreController {
    private final RiskScoreService service;

    @GetMapping("/page")
    public Result<IPage<RiskScore>> page(@RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody RiskScore entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody RiskScore entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/review/{id}")
    public Result<Void> review(@PathVariable Long id) {
        service.updateStatus(id, "REVIEWED");
        return Result.success();
    }

    @PutMapping("/remediate/{id}")
    public Result<Void> remediate(@PathVariable Long id) {
        service.updateStatus(id, "REMEDIATED");
        return Result.success();
    }

}
