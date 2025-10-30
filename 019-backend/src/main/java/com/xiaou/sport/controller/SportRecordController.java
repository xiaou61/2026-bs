package com.xiaou.sport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.SportRecord;
import com.xiaou.sport.service.SportRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/sport")
public class SportRecordController {

    @Autowired
    private SportRecordService sportRecordService;

    @PostMapping("/record")
    public Result<Void> createRecord(@RequestAttribute Long userId, @RequestBody SportRecord record) {
        record.setUserId(userId);

        if (record.getDuration() != null && record.getDistance() != null
                && record.getDistance().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal avgSpeed = new BigDecimal(record.getDuration()).divide(record.getDistance(), 2,
                    java.math.RoundingMode.HALF_UP);
            record.setAvgSpeed(avgSpeed);
        }

        int points = calculatePoints(record);
        record.setPointsEarned(points);

        sportRecordService.save(record);
        return Result.success();
    }

    @GetMapping("/records")
    public Result<Page<SportRecord>> getRecords(@RequestAttribute Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<SportRecord> pageInfo = new Page<>(page, size);
        LambdaQueryWrapper<SportRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SportRecord::getUserId, userId)
                .orderByDesc(SportRecord::getSportDate);
        sportRecordService.page(pageInfo, wrapper);
        return Result.success(pageInfo);
    }

    @GetMapping("/record/{id}")
    public Result<SportRecord> getRecord(@PathVariable Long id) {
        SportRecord record = sportRecordService.getById(id);
        return Result.success(record);
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> getStats(@RequestAttribute Long userId) {
        Map<String, Object> stats = sportRecordService.getStats(userId);
        return Result.success(stats);
    }

    private int calculatePoints(SportRecord record) {
        int points = 0;
        if ("running".equals(record.getSportType())) {
            if (record.getDistance() != null) {
                points += record.getDistance().intValue() * 10;
            }
            if (record.getDuration() != null && record.getDuration() >= 30) {
                points += 5;
            }
        } else if ("gym".equals(record.getSportType())) {
            if (record.getDuration() != null) {
                points += (record.getDuration() / 30) * 10;
            }
        } else {
            if (record.getDuration() != null) {
                points += (record.getDuration() / 60) * 15;
            }
        }
        return points;
    }
}
