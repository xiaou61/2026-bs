package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.QualityResult;
import com.servicequality.mapper.QualityResultMapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class QualityResultService extends ServiceImpl<QualityResultMapper, QualityResult> {
    public Page<QualityResult> page(Integer pageNum, Integer pageSize, String keyword, Long orderId, Long agentId, Integer reviewStatus) {
        LambdaQueryWrapper<QualityResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(QualityResult::getRiskLevel, keyword).or().like(QualityResult::getHitRules, keyword).or().like(QualityResult::getSuggestion, keyword));
        wrapper.eq(orderId != null, QualityResult::getOrderId, orderId);
        wrapper.eq(agentId != null, QualityResult::getAgentId, agentId);
        wrapper.eq(reviewStatus != null, QualityResult::getReviewStatus, reviewStatus);
        wrapper.orderByDesc(QualityResult::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(QualityResult entity) {
        if (entity.getId() == null) {
            
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void review(QualityResult source) {
        QualityResult entity = getById(source.getId());
        if (entity == null) {
            throw new BusinessException(400, "质检结果不存在");
        }
        entity.setReviewStatus(source.getReviewStatus() == null ? 1 : source.getReviewStatus());
        entity.setReviewComment(source.getReviewComment() == null ? "质检复核完成" : source.getReviewComment());
        entity.setUpdateTime(LocalDateTime.now());
        updateById(entity);
    }

}
