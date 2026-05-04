package com.promptops.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.promptops.common.BusinessException;
import com.promptops.entity.EvaluationResult;
import com.promptops.mapper.EvaluationResultMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EvaluationResultService extends ServiceImpl<EvaluationResultMapper, EvaluationResult> {

    public Page<EvaluationResult> page(Integer pageNum, Integer pageSize, Long taskId, Integer reviewStatus) {
        LambdaQueryWrapper<EvaluationResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(taskId != null, EvaluationResult::getTaskId, taskId);
        wrapper.eq(reviewStatus != null, EvaluationResult::getReviewStatus, reviewStatus);
        wrapper.orderByDesc(EvaluationResult::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void review(EvaluationResult entity) {
        EvaluationResult result = getById(entity.getId());
        if (result == null) {
            throw new BusinessException(400, "评测结果不存在");
        }
        result.setReviewStatus(entity.getReviewStatus() == null ? 1 : entity.getReviewStatus());
        result.setReviewComment(entity.getReviewComment());
        result.setUpdateTime(LocalDateTime.now());
        updateById(result);
    }
}
