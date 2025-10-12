package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.Reward;
import com.xiaou.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @GetMapping
    public Result<?> list(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer size,
                         @RequestParam(required = false) Integer status) {
        Page<Reward> pageParam = new Page<>(page, size);
        QueryWrapper<Reward> wrapper = new QueryWrapper<>();
        
        if (status != null) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByDesc("create_time");
        IPage<Reward> result = rewardService.page(pageParam, wrapper);
        
        return Result.success(result);
    }

    @GetMapping("/available")
    public Result<?> available() {
        List<Reward> list = rewardService.getAvailableRewards();
        return Result.success(list);
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        Reward reward = rewardService.getById(id);
        return Result.success(reward);
    }

    @PostMapping
    public Result<?> create(@RequestBody Reward reward) {
        reward.setExchangeCount(0);
        reward.setStatus(1);
        reward.setCreateTime(LocalDateTime.now());
        reward.setUpdateTime(LocalDateTime.now());
        rewardService.save(reward);
        return Result.success(reward);
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Reward reward) {
        reward.setId(id);
        reward.setUpdateTime(LocalDateTime.now());
        rewardService.updateById(reward);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        rewardService.removeById(id);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        Reward reward = rewardService.getById(id);
        if (reward == null) {
            return Result.error("商品不存在");
        }
        
        reward.setStatus(params.get("status"));
        reward.setUpdateTime(LocalDateTime.now());
        rewardService.updateById(reward);
        return Result.success();
    }
}

