package com.recruitmatch.service;

import com.recruitmatch.common.BusinessException;
import com.recruitmatch.entity.CandidateProfile;
import com.recruitmatch.entity.JobPosition;
import com.recruitmatch.entity.JobRequirement;
import com.recruitmatch.entity.MatchResult;
import com.recruitmatch.entity.MatchTask;
import com.recruitmatch.mapper.CandidateProfileMapper;
import com.recruitmatch.mapper.JobPositionMapper;
import com.recruitmatch.mapper.JobRequirementMapper;
import com.recruitmatch.mapper.MatchResultMapper;
import com.recruitmatch.mapper.MatchTaskMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

@Service
public class MatchTaskService extends ServiceImpl<MatchTaskMapper, MatchTask> {
    @Autowired
    private CandidateProfileMapper candidateProfileMapper;

    @Autowired
    private JobPositionMapper jobPositionMapper;

    @Autowired
    private JobRequirementMapper jobRequirementMapper;

    @Autowired
    private MatchResultMapper matchResultMapper;

    public Page<MatchTask> page(Integer pageNum, Integer pageSize, String keyword, Integer status, String priority) {
        LambdaQueryWrapper<MatchTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(MatchTask::getTaskNo, keyword).or().like(MatchTask::getTaskName, keyword));
        wrapper.eq(status != null, MatchTask::getStatus, status);
        wrapper.eq(StringUtils.hasText(priority), MatchTask::getPriority, priority);
        wrapper.orderByDesc(MatchTask::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(MatchTask entity, Long userId) {
        if (entity.getId() == null) {
            entity.setTaskNo(entity.getTaskNo() == null ? "MT" + System.currentTimeMillis() : entity.getTaskNo());
            entity.setStatus(entity.getStatus() == null ? 0 : entity.getStatus());
            entity.setHandlerId(userId);
            entity.setCreateTime(LocalDateTime.now());
        }
        saveOrUpdate(entity);
    }

    public void run(Long id) {
        MatchTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "匹配任务不存在");
        }
        CandidateProfile candidate = candidateProfileMapper.selectById(task.getCandidateId());
        JobPosition job = jobPositionMapper.selectById(task.getJobId());
        if (candidate == null || job == null) {
            throw new BusinessException(400, "候选人或岗位不存在");
        }
        List<JobRequirement> requirements = jobRequirementMapper.selectList(new LambdaQueryWrapper<JobRequirement>().eq(JobRequirement::getJobId, job.getId()).eq(JobRequirement::getStatus, 1));
        String text = (candidate.getSkillTags() == null ? "" : candidate.getSkillTags()) + "," + (candidate.getTargetJob() == null ? "" : candidate.getTargetJob()) + "," + (candidate.getEducation() == null ? "" : candidate.getEducation());
        BigDecimal score = new BigDecimal("35");
        StringJoiner matched = new StringJoiner(",");
        StringJoiner missing = new StringJoiner(",");
        for (JobRequirement requirement : requirements) {
            if (StringUtils.hasText(requirement.getKeyword()) && text.contains(requirement.getKeyword())) {
                matched.add(requirement.getKeyword());
                score = score.add(requirement.getWeight() == null ? BigDecimal.TEN : requirement.getWeight().multiply(new BigDecimal("15")));
            } else {
                missing.add(requirement.getKeyword());
            }
        }
        if (score.compareTo(new BigDecimal("98")) > 0) {
            score = new BigDecimal("98");
        }
        String level = score.compareTo(new BigDecimal("82")) >= 0 ? "强推荐" : score.compareTo(new BigDecimal("65")) >= 0 ? "可面试" : "暂缓";
        MatchResult result = new MatchResult();
        result.setTaskId(id);
        result.setCandidateId(candidate.getId());
        result.setJobId(job.getId());
        result.setMatchedSkills(matched.length() == 0 ? "暂无强匹配项" : matched.toString());
        result.setMissingSkills(missing.length() == 0 ? "核心要求均已覆盖" : missing.toString());
        result.setMatchScore(score);
        result.setRecommendLevel(level);
        result.setConclusion(candidate.getRealName() + " 对岗位 " + job.getJobName() + " 的匹配等级为" + level);
        result.setReviewStatus(0);
        result.setCreateTime(LocalDateTime.now());
        result.setUpdateTime(LocalDateTime.now());
        matchResultMapper.insert(result);
        task.setStatus(1);
        updateById(task);
    }

    public void finish(Long id) {
        MatchTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "匹配任务不存在");
        }
        task.setStatus(2);
        task.setFinishTime(LocalDateTime.now());
        updateById(task);
    }

    public void reject(Long id) {
        MatchTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "匹配任务不存在");
        }
        task.setStatus(3);
        updateById(task);
    }
}
