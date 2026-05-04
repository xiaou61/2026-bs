package com.recruitmatch.service;

import com.recruitmatch.entity.InterviewPlan;
import com.recruitmatch.mapper.InterviewPlanMapper;
import com.recruitmatch.common.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class InterviewPlanService extends ServiceImpl<InterviewPlanMapper, InterviewPlan> {
    public Page<InterviewPlan> page(Integer pageNum, Integer pageSize, Long candidateId, Long jobId, Integer status, String interviewType) {
        LambdaQueryWrapper<InterviewPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(candidateId != null, InterviewPlan::getCandidateId, candidateId);
        wrapper.eq(jobId != null, InterviewPlan::getJobId, jobId);
        wrapper.eq(status != null, InterviewPlan::getStatus, status);
        wrapper.eq(StringUtils.hasText(interviewType), InterviewPlan::getInterviewType, interviewType);
        wrapper.orderByDesc(InterviewPlan::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(InterviewPlan entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 0 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void updateStatus(Long id, Integer status) {
        InterviewPlan plan = getById(id);
        if (plan == null) {
            throw new BusinessException(400, "面试计划不存在");
        }
        plan.setStatus(status);
        plan.setUpdateTime(LocalDateTime.now());
        updateById(plan);
    }
}
