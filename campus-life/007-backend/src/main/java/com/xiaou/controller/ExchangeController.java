package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.ExchangeRecord;
import com.xiaou.entity.Reward;
import com.xiaou.entity.User;
import com.xiaou.service.ExchangeRecordService;
import com.xiaou.service.PointsRecordService;
import com.xiaou.service.RewardService;
import com.xiaou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exchanges")
public class ExchangeController {

    @Autowired
    private ExchangeRecordService exchangeRecordService;

    @Autowired
    private RewardService rewardService;

    @Autowired
    private UserService userService;

    @Autowired
    private PointsRecordService pointsRecordService;

    @PostMapping
    public Result<?> exchange(@RequestBody ExchangeRecord record, @RequestAttribute Long userId) {
        Reward reward = rewardService.getById(record.getRewardId());
        if (reward == null) {
            return Result.error("商品不存在");
        }

        if (reward.getStatus() != 1) {
            return Result.error("商品已下架");
        }

        if (reward.getStock() <= 0) {
            return Result.error("商品库存不足");
        }

        User user = userService.getById(userId);
        if (user.getAvailablePoints() < reward.getPoints()) {
            return Result.error("积分不足");
        }

        if (!rewardService.decrementStock(reward.getId())) {
            return Result.error("兑换失败，库存不足");
        }

        userService.updatePoints(userId, -reward.getPoints());

        record.setUserId(userId);
        record.setUserName(user.getName());
        record.setRewardName(reward.getName());
        record.setPoints(reward.getPoints());
        record.setPhone(user.getPhone());
        record.setStatus(0);
        record.setExchangeTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        exchangeRecordService.save(record);

        User updatedUser = userService.getById(userId);
        pointsRecordService.addRecord(userId, user.getName(), 2, -reward.getPoints(), 
                updatedUser.getAvailablePoints(), record.getId(), reward.getName(), 
                "兑换商品消耗积分");

        return Result.success();
    }

    @GetMapping
    public Result<?> list(@RequestParam(required = false) Integer status) {
        QueryWrapper<ExchangeRecord> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("exchange_time");
        
        List<ExchangeRecord> list = exchangeRecordService.list(wrapper);
        return Result.success(list);
    }

    @GetMapping("/my")
    public Result<?> myExchanges(@RequestAttribute Long userId) {
        List<ExchangeRecord> list = exchangeRecordService.getUserExchanges(userId);
        return Result.success(list);
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        ExchangeRecord record = exchangeRecordService.getById(id);
        if (record == null) {
            return Result.error("兑换记录不存在");
        }

        Integer newStatus = params.get("status");
        Integer oldStatus = record.getStatus();

        if (newStatus == 2 && oldStatus == 0) {
            rewardService.incrementStock(record.getRewardId());
            
            userService.updatePoints(record.getUserId(), record.getPoints());
            
            User user = userService.getById(record.getUserId());
            pointsRecordService.addRecord(record.getUserId(), user.getName(), 2, 
                    record.getPoints(), user.getAvailablePoints(), record.getId(), 
                    record.getRewardName(), "取消兑换退回积分");
        }

        record.setStatus(newStatus);
        record.setUpdateTime(LocalDateTime.now());
        exchangeRecordService.updateById(record);

        return Result.success();
    }
}

