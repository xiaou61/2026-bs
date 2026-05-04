package com.recruitmatch.service;

import com.recruitmatch.entity.MatchResult;
import com.recruitmatch.mapper.MatchResultMapper;
import com.recruitmatch.common.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class MatchResultService extends ServiceImpl<MatchResultMapper, MatchResult> {
    public Page<MatchResult> page(Integer pageNum, Integer pageSize, Long candidateId, Long jobId, String recommendLevel, Integer reviewStatus) {
        LambdaQueryWrapper<MatchResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(candidateId != null, MatchResult::getCandidateId, candidateId);
        wrapper.eq(jobId != null, MatchResult::getJobId, jobId);
        wrapper.eq(StringUtils.hasText(recommendLevel), MatchResult::getRecommendLevel, recommendLevel);
        wrapper.eq(reviewStatus != null, MatchResult::getReviewStatus, reviewStatus);
        wrapper.orderByDesc(MatchResult::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void review(MatchResult entity) {
        MatchResult result = getById(entity.getId());
        if (result == null) {
            throw new BusinessException(400, "匹配结果不存在");
        }
        result.setReviewStatus(entity.getReviewStatus() == null ? 1 : entity.getReviewStatus());
        result.setReviewComment(StringUtils.hasText(entity.getReviewComment()) ? entity.getReviewComment() : "匹配复核完成");
        result.setUpdateTime(LocalDateTime.now());
        updateById(result);
    }
}
