package com.xiaou.sport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.SportRecord;
import com.xiaou.sport.service.SportRecordService;
import com.xiaou.sport.service.UserService;
import com.xiaou.sport.utils.PointRuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/sport")
public class SportRecordController {

    @Autowired
    private SportRecordService sportRecordService;

    @Autowired
    private UserService userService;

    @PostMapping("/record")
    @Transactional
    public Result<Void> createRecord(@RequestAttribute Long userId, @RequestBody SportRecord record) {
        record.setUserId(userId);
        if (record.getSportDate() == null) {
            record.setSportDate(LocalDate.now());
        }

        if (record.getDuration() != null && record.getDistance() != null
                && record.getDistance().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal avgSpeed = new BigDecimal(record.getDuration()).divide(record.getDistance(), 2,
                    java.math.RoundingMode.HALF_UP);
            record.setAvgSpeed(avgSpeed);
        }

        int points = PointRuleUtil.calculateSportPoints(record);
        record.setPointsEarned(points);

        if (!sportRecordService.save(record)) {
            return Result.error("创建运动记录失败");
        }
        addUserPoints(userId, points);
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
    public Result<SportRecord> getRecord(@PathVariable Long id, @RequestAttribute Long userId) {
        LambdaQueryWrapper<SportRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SportRecord::getId, id)
                .eq(SportRecord::getUserId, userId);
        SportRecord record = sportRecordService.getOne(wrapper);
        if (record == null) {
            return Result.error("记录不存在");
        }
        return Result.success(record);
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> getStats(@RequestAttribute Long userId) {
        Map<String, Object> stats = sportRecordService.getStats(userId);
        var user = userService.getById(userId);
        stats.put("totalPoints", user != null && user.getPoints() != null ? user.getPoints() : 0);
        return Result.success(stats);
    }

    private void addUserPoints(Long userId, int points) {
        if (points <= 0) {
            return;
        }
        var user = userService.getById(userId);
        if (user == null) {
            return;
        }
        int currentPoints = user.getPoints() == null ? 0 : user.getPoints();
        user.setPoints(currentPoints + points);
        userService.updateById(user);
    }
}
