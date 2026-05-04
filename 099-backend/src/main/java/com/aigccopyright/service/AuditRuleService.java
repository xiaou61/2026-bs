package com.aigccopyright.service;

import com.aigccopyright.entity.AuditRule;
import com.aigccopyright.mapper.AuditRuleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class AuditRuleService extends ServiceImpl<AuditRuleMapper, AuditRule> {

    public Page<AuditRule> page(Integer pageNum, Integer pageSize, String keyword, String ruleType, Integer status) {
        LambdaQueryWrapper<AuditRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(AuditRule::getRuleName, keyword).or().like(AuditRule::getKeywords, keyword));
        wrapper.eq(StringUtils.hasText(ruleType), AuditRule::getRuleType, ruleType);
        wrapper.eq(status != null, AuditRule::getStatus, status);
        wrapper.orderByDesc(AuditRule::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(AuditRule entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }
}
