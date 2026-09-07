package com.promptops.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.promptops.entity.ScoreRule;
import com.promptops.mapper.ScoreRuleMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class ScoreRuleService extends ServiceImpl<ScoreRuleMapper, ScoreRule> {

    public Page<ScoreRule> page(Integer pageNum, Integer pageSize, String keyword, Integer status) {
        LambdaQueryWrapper<ScoreRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), w -> w.like(ScoreRule::getName, keyword).or().like(ScoreRule::getDimension, keyword));
        wrapper.eq(status != null, ScoreRule::getStatus, status);
        wrapper.orderByDesc(ScoreRule::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(ScoreRule entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }
}
