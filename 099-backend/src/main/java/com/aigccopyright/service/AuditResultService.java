package com.aigccopyright.service;

import com.aigccopyright.common.BusinessException;
import com.aigccopyright.entity.AuditResult;
import com.aigccopyright.mapper.AuditResultMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditResultService extends ServiceImpl<AuditResultMapper, AuditResult> {

    public Page<AuditResult> page(Integer pageNum, Integer pageSize, Long taskId, String riskLevel, Integer reviewStatus) {
        LambdaQueryWrapper<AuditResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(taskId != null, AuditResult::getTaskId, taskId);
        wrapper.eq(riskLevel != null && !riskLevel.isBlank(), AuditResult::getRiskLevel, riskLevel);
        wrapper.eq(reviewStatus != null, AuditResult::getReviewStatus, reviewStatus);
        wrapper.orderByDesc(AuditResult::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void review(AuditResult entity) {
        AuditResult result = getById(entity.getId());
        if (result == null) {
            throw new BusinessException(400, "审核结果不存在");
        }
        result.setReviewStatus(entity.getReviewStatus() == null ? 1 : entity.getReviewStatus());
        result.setReviewComment(entity.getReviewComment());
        result.setUpdateTime(LocalDateTime.now());
        updateById(result);
    }
}
