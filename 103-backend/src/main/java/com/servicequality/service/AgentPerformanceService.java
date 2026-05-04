package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.AgentPerformance;
import com.servicequality.mapper.AgentPerformanceMapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class AgentPerformanceService extends ServiceImpl<AgentPerformanceMapper, AgentPerformance> {
    public Page<AgentPerformance> page(Integer pageNum, Integer pageSize, String keyword, Long agentId, String rankLevel) {
        LambdaQueryWrapper<AgentPerformance> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(AgentPerformance::getStatDate, keyword).or().like(AgentPerformance::getRankLevel, keyword));
        wrapper.eq(agentId != null, AgentPerformance::getAgentId, agentId);
        wrapper.eq(StringUtils.hasText(rankLevel), AgentPerformance::getRankLevel, rankLevel);
        wrapper.orderByDesc(AgentPerformance::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(AgentPerformance entity) {
        if (entity.getId() == null) {
            
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

}
