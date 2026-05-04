package com.recruitmatch.service;

import com.recruitmatch.entity.ParsingResult;
import com.recruitmatch.mapper.ParsingResultMapper;
import com.recruitmatch.common.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class ParsingResultService extends ServiceImpl<ParsingResultMapper, ParsingResult> {
    public Page<ParsingResult> page(Integer pageNum, Integer pageSize, Long resumeId, Long candidateId, Integer reviewStatus) {
        LambdaQueryWrapper<ParsingResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(resumeId != null, ParsingResult::getResumeId, resumeId);
        wrapper.eq(candidateId != null, ParsingResult::getCandidateId, candidateId);
        wrapper.eq(reviewStatus != null, ParsingResult::getReviewStatus, reviewStatus);
        wrapper.orderByDesc(ParsingResult::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void review(ParsingResult entity) {
        ParsingResult result = getById(entity.getId());
        if (result == null) {
            throw new BusinessException(400, "解析结果不存在");
        }
        result.setReviewStatus(entity.getReviewStatus() == null ? 1 : entity.getReviewStatus());
        result.setReviewComment(StringUtils.hasText(entity.getReviewComment()) ? entity.getReviewComment() : "解析复核完成");
        result.setUpdateTime(LocalDateTime.now());
        updateById(result);
    }
}
