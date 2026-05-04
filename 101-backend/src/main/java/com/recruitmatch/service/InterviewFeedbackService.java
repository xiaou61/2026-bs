package com.recruitmatch.service;

import com.recruitmatch.entity.InterviewFeedback;
import com.recruitmatch.mapper.InterviewFeedbackMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class InterviewFeedbackService extends ServiceImpl<InterviewFeedbackMapper, InterviewFeedback> {
    public Page<InterviewFeedback> page(Integer pageNum, Integer pageSize, String keyword, Long planId, Integer resultStatus) {
        LambdaQueryWrapper<InterviewFeedback> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(InterviewFeedback::getStrengths, keyword).or().like(InterviewFeedback::getWeaknesses, keyword).or().like(InterviewFeedback::getSuggestion, keyword));
        wrapper.eq(planId != null, InterviewFeedback::getPlanId, planId);
        wrapper.eq(resultStatus != null, InterviewFeedback::getResultStatus, resultStatus);
        wrapper.orderByDesc(InterviewFeedback::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }
}
