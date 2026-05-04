package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.QualityRule;
import com.servicequality.mapper.QualityRuleMapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class QualityRuleService extends ServiceImpl<QualityRuleMapper, QualityRule> {
    public Page<QualityRule> page(Integer pageNum, Integer pageSize, String keyword, String ruleType, Integer status) {
        LambdaQueryWrapper<QualityRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(QualityRule::getRuleName, keyword).or().like(QualityRule::getRuleType, keyword).or().like(QualityRule::getKeyword, keyword));
        wrapper.eq(StringUtils.hasText(ruleType), QualityRule::getRuleType, ruleType);
        wrapper.eq(status != null, QualityRule::getStatus, status);
        wrapper.orderByDesc(QualityRule::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(QualityRule entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

}
