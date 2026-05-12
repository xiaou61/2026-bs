package com.recruitmatch.service;

import com.recruitmatch.common.BusinessException;
import com.recruitmatch.entity.CandidateProfile;
import com.recruitmatch.entity.CertificateRecord;
import com.recruitmatch.entity.ParsingResult;
import com.recruitmatch.entity.ParsingTask;
import com.recruitmatch.entity.ResumeFile;
import com.recruitmatch.mapper.CandidateProfileMapper;
import com.recruitmatch.mapper.CertificateRecordMapper;
import com.recruitmatch.mapper.ParsingResultMapper;
import com.recruitmatch.mapper.ParsingTaskMapper;
import com.recruitmatch.mapper.ResumeFileMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParsingTaskService extends ServiceImpl<ParsingTaskMapper, ParsingTask> {
    @Autowired
    private ResumeFileMapper resumeFileMapper;

    @Autowired
    private CandidateProfileMapper candidateProfileMapper;

    @Autowired
    private CertificateRecordMapper certificateRecordMapper;

    @Autowired
    private ParsingResultMapper parsingResultMapper;

    public Page<ParsingTask> page(Integer pageNum, Integer pageSize, String keyword, Integer status, String priority) {
        LambdaQueryWrapper<ParsingTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(ParsingTask::getTaskNo, keyword).or().like(ParsingTask::getTaskName, keyword));
        wrapper.eq(status != null, ParsingTask::getStatus, status);
        wrapper.eq(StringUtils.hasText(priority), ParsingTask::getPriority, priority);
        wrapper.orderByDesc(ParsingTask::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(ParsingTask entity, Long userId) {
        if (entity.getId() == null) {
            entity.setTaskNo(entity.getTaskNo() == null ? "PT" + System.currentTimeMillis() : entity.getTaskNo());
            entity.setStatus(0);
            entity.setHandlerId(userId);
            entity.setCreateTime(LocalDateTime.now());
        } else {
            ParsingTask db = getById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "解析任务不存在");
            }
            entity.setTaskNo(db.getTaskNo());
            entity.setStatus(db.getStatus());
            entity.setHandlerId(db.getHandlerId());
            entity.setCreateTime(db.getCreateTime());
            entity.setFinishTime(db.getFinishTime());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void run(Long id) {
        ParsingTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "解析任务不存在");
        }
        if (task.getStatus() == null || task.getStatus() != 0) {
            throw new BusinessException(400, "仅待处理任务可以启动");
        }
        ResumeFile resume = resumeFileMapper.selectById(task.getResumeId());
        if (resume == null) {
            throw new BusinessException(400, "简历材料不存在");
        }
        CandidateProfile candidate = candidateProfileMapper.selectById(resume.getCandidateId());
        List<CertificateRecord> certificates = certificateRecordMapper.selectList(new LambdaQueryWrapper<CertificateRecord>().eq(CertificateRecord::getCandidateId, resume.getCandidateId()).eq(CertificateRecord::getVerifyStatus, 1));
        BigDecimal score = new BigDecimal(45 + safe(resume.getWorkYears()) * 8 + certificates.size() * 8 + skillCount(resume.getSkills()) * 5);
        if (score.compareTo(new BigDecimal("98")) > 0) {
            score = new BigDecimal("98");
        }
        ParsingResult result = parsingResultMapper.selectOne(new LambdaQueryWrapper<ParsingResult>().eq(ParsingResult::getTaskId, id).last("limit 1"));
        if (result == null) {
            result = new ParsingResult();
            result.setTaskId(id);
            result.setCreateTime(LocalDateTime.now());
        }
        result.setResumeId(resume.getId());
        result.setCandidateId(resume.getCandidateId());
        result.setExtractedEducation(resume.getEducation());
        result.setExtractedSkills(resume.getSkills());
        result.setExtractedExperience(safe(resume.getWorkYears()) + "年工作经验，已核验证书" + certificates.size() + "项");
        result.setScore(score);
        result.setConclusion(score.compareTo(new BigDecimal("80")) >= 0 ? "材料完整度高，适合进入岗位匹配" : "材料仍需补充证书或项目经历");
        result.setReviewStatus(0);
        result.setReviewComment(null);
        result.setUpdateTime(LocalDateTime.now());
        if (result.getId() == null) parsingResultMapper.insert(result);
        else parsingResultMapper.updateById(result);
        resume.setParseStatus(1);
        resume.setUpdateTime(LocalDateTime.now());
        resumeFileMapper.updateById(resume);
        if (candidate != null) {
            candidate.setEducation(resume.getEducation());
            candidate.setWorkYears(resume.getWorkYears());
            candidate.setSkillTags(resume.getSkills());
            candidate.setUpdateTime(LocalDateTime.now());
            candidateProfileMapper.updateById(candidate);
        }
        task.setStatus(1);
        task.setUpdateTime(LocalDateTime.now());
        updateById(task);
    }

    public void finish(Long id) {
        ParsingTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "解析任务不存在");
        }
        if (task.getStatus() == null || task.getStatus() != 1) {
            throw new BusinessException(400, "仅已执行任务可以完成");
        }
        task.setStatus(2);
        task.setFinishTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        updateById(task);
    }

    public void reject(Long id) {
        ParsingTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "解析任务不存在");
        }
        if (task.getStatus() == null || (task.getStatus() != 0 && task.getStatus() != 1)) {
            throw new BusinessException(400, "仅待处理或已执行任务可以驳回");
        }
        task.setStatus(3);
        task.setUpdateTime(LocalDateTime.now());
        updateById(task);
    }

    private int safe(Integer value) {
        return value == null ? 0 : value;
    }

    private int skillCount(String skills) {
        return StringUtils.hasText(skills) ? skills.split(",").length : 0;
    }
}
