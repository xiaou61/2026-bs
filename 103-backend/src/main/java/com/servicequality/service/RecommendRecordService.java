package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.RecommendRecord;
import com.servicequality.mapper.RecommendRecordMapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class RecommendRecordService extends ServiceImpl<RecommendRecordMapper, RecommendRecord> {
    public Page<RecommendRecord> page(Integer pageNum, Integer pageSize, String keyword, Long orderId, Long agentId, Integer adoptStatus) {
        LambdaQueryWrapper<RecommendRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(RecommendRecord::getMatchKeyword, keyword).or().like(RecommendRecord::getFeedback, keyword));
        wrapper.eq(orderId != null, RecommendRecord::getOrderId, orderId);
        wrapper.eq(agentId != null, RecommendRecord::getAgentId, agentId);
        wrapper.eq(adoptStatus != null, RecommendRecord::getAdoptStatus, adoptStatus);
        wrapper.orderByDesc(RecommendRecord::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(RecommendRecord entity) {
        if (entity.getId() == null) {
            
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void adopt(Long id) {
        RecommendRecord entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "推荐记录不存在");
        }
        entity.setAdoptStatus(1);
        entity.setFeedback(entity.getFeedback() == null ? "坐席已采纳推荐知识" : entity.getFeedback());
        entity.setUpdateTime(LocalDateTime.now());
        updateById(entity);
    }

    public void reject(Long id) {
        RecommendRecord entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "推荐记录不存在");
        }
        entity.setAdoptStatus(2);
        entity.setUpdateTime(LocalDateTime.now());
        updateById(entity);
    }

}
