package com.crossborder.controller;

import com.crossborder.common.Result;
import com.crossborder.entity.LogisticsTrack;
import com.crossborder.service.LogisticsTrackService;
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
@RequestMapping("/api/logistics")
@RequiredArgsConstructor
public class LogisticsTrackController {
    private final LogisticsTrackService service;

    @GetMapping("/page")
    public Result<IPage<LogisticsTrack>> page(@RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody LogisticsTrack entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody LogisticsTrack entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/depart/{id}")
    public Result<Void> depart(@PathVariable Long id) {
        service.updateStatus(id, "IN_TRANSIT");
        return Result.success();
    }

    @PutMapping("/arrive/{id}")
    public Result<Void> arrive(@PathVariable Long id) {
        service.updateStatus(id, "ARRIVED");
        return Result.success();
    }

    @PutMapping("/sign/{id}")
    public Result<Void> sign(@PathVariable Long id) {
        service.updateStatus(id, "SIGNED");
        return Result.success();
    }

}
