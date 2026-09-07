package com.recruitmatch.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recruitmatch.common.BusinessException;
import com.recruitmatch.entity.InterviewFeedback;
import com.recruitmatch.entity.InterviewPlan;
import com.recruitmatch.mapper.InterviewFeedbackMapper;
import com.recruitmatch.mapper.InterviewPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class InterviewFeedbackService extends ServiceImpl<InterviewFeedbackMapper, InterviewFeedback> {
    @Autowired
    private InterviewPlanMapper interviewPlanMapper;

    public Page<InterviewFeedback> pageByRole(Integer pageNum, Integer pageSize, String keyword, Long planId, Integer resultStatus, Long userId, String role) {
        LambdaQueryWrapper<InterviewFeedback> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(InterviewFeedback::getStrengths, keyword).or().like(InterviewFeedback::getWeaknesses, keyword).or().like(InterviewFeedback::getSuggestion, keyword));
        wrapper.eq(planId != null, InterviewFeedback::getPlanId, planId);
        wrapper.eq(resultStatus != null, InterviewFeedback::getResultStatus, resultStatus);
        wrapper.eq("INTERVIEWER".equals(role), InterviewFeedback::getInterviewerId, userId);
        wrapper.orderByDesc(InterviewFeedback::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(InterviewFeedback entity, Long currentUserId) {
        InterviewPlan plan = interviewPlanMapper.selectById(entity.getPlanId());
        if (plan == null) {
            throw new BusinessException(400, "面试计划不存在");
        }
        if (!currentUserId.equals(plan.getInterviewerId())) {
            throw new BusinessException(403, "只能填写分配给自己的面试反馈");
        }
        if (plan.getStatus() == null || (plan.getStatus() != 1 && plan.getStatus() != 3)) {
            throw new BusinessException(400, "仅已确认或已完成的面试可以填写反馈");
        }
        if (entity.getId() != null) {
            InterviewFeedback db = getById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "面试反馈不存在");
            }
            if (!currentUserId.equals(db.getInterviewerId())) {
                throw new BusinessException(403, "只能编辑自己的面试反馈");
            }
            InterviewFeedback existed = getOne(new LambdaQueryWrapper<InterviewFeedback>()
                    .eq(InterviewFeedback::getPlanId, entity.getPlanId())
                    .eq(InterviewFeedback::getInterviewerId, currentUserId)
                    .ne(InterviewFeedback::getId, entity.getId())
                    .last("limit 1"));
            if (existed != null) {
                throw new BusinessException(400, "当前面试计划已存在你的反馈记录");
            }
            entity.setCreateTime(db.getCreateTime());
        } else {
            InterviewFeedback existed = getOne(new LambdaQueryWrapper<InterviewFeedback>()
                    .eq(InterviewFeedback::getPlanId, entity.getPlanId())
                    .eq(InterviewFeedback::getInterviewerId, currentUserId)
                    .last("limit 1"));
            if (existed != null) {
                throw new BusinessException(400, "当前面试计划已存在你的反馈记录");
            }
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setInterviewerId(currentUserId);
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void deleteByRole(Long id, Long currentUserId) {
        InterviewFeedback entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "面试反馈不存在");
        }
        if (!currentUserId.equals(entity.getInterviewerId())) {
            throw new BusinessException(403, "只能删除自己的面试反馈");
        }
        removeById(id);
    }
}
