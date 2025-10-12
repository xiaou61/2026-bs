package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Reward;
import com.xiaou.mapper.RewardMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RewardService extends ServiceImpl<RewardMapper, Reward> {

    public List<Reward> getAvailableRewards() {
        return this.list(new QueryWrapper<Reward>()
                .eq("status", 1)
                .gt("stock", 0)
                .orderByDesc("create_time"));
    }

    public boolean decrementStock(Long rewardId) {
        Reward reward = this.getById(rewardId);
        if (reward != null && reward.getStock() > 0) {
            reward.setStock(reward.getStock() - 1);
            reward.setExchangeCount(reward.getExchangeCount() + 1);
            reward.setUpdateTime(LocalDateTime.now());
            return this.updateById(reward);
        }
        return false;
    }

    public void incrementStock(Long rewardId) {
        Reward reward = this.getById(rewardId);
        if (reward != null) {
            reward.setStock(reward.getStock() + 1);
            reward.setExchangeCount(reward.getExchangeCount() - 1);
            reward.setUpdateTime(LocalDateTime.now());
            this.updateById(reward);
        }
    }
}

