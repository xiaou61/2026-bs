package com.recruitmatch.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recruitmatch.common.BusinessException;
import com.recruitmatch.entity.InterviewPlan;
import com.recruitmatch.mapper.InterviewPlanMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class InterviewPlanService extends ServiceImpl<InterviewPlanMapper, InterviewPlan> {
    public Page<InterviewPlan> pageByRole(Integer pageNum, Integer pageSize, Long candidateId, Long jobId, Integer status, String interviewType, Long userId, String role) {
        LambdaQueryWrapper<InterviewPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(candidateId != null, InterviewPlan::getCandidateId, candidateId);
        wrapper.eq(jobId != null, InterviewPlan::getJobId, jobId);
        wrapper.eq(status != null, InterviewPlan::getStatus, status);
        wrapper.eq(StringUtils.hasText(interviewType), InterviewPlan::getInterviewType, interviewType);
        wrapper.eq("INTERVIEWER".equals(role), InterviewPlan::getInterviewerId, userId);
        wrapper.orderByDesc(InterviewPlan::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(InterviewPlan entity) {
        if (entity.getId() == null) {
            entity.setStatus(0);
            entity.setCreateTime(LocalDateTime.now());
        } else {
            InterviewPlan db = getById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "面试计划不存在");
            }
            entity.setCreateTime(db.getCreateTime());
            entity.setStatus(db.getStatus());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void updateStatus(Long id, Integer status, Long currentUserId, String role) {
        InterviewPlan plan = getById(id);
        if (plan == null) {
            throw new BusinessException(400, "面试计划不存在");
        }
        if ("INTERVIEWER".equals(role) && !currentUserId.equals(plan.getInterviewerId())) {
            throw new BusinessException(403, "只能处理分配给自己的面试计划");
        }
        Integer currentStatus = plan.getStatus();
        if (status != null && status == 1 && (currentStatus == null || currentStatus != 0)) {
            throw new BusinessException(400, "仅待确认面试可以确认");
        }
        if (status != null && status == 2 && (currentStatus == null || (currentStatus != 0 && currentStatus != 1))) {
            throw new BusinessException(400, "仅待确认或已确认面试可以取消");
        }
        if (status != null && status == 3 && (currentStatus == null || currentStatus != 1)) {
            throw new BusinessException(400, "仅已确认面试可以完成");
        }
        plan.setStatus(status);
        plan.setUpdateTime(LocalDateTime.now());
        updateById(plan);
    }
}
