package com.recruitmatch.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recruitmatch.common.BusinessException;
import com.recruitmatch.entity.CandidateProfile;
import com.recruitmatch.entity.ResumeFile;
import com.recruitmatch.mapper.CandidateProfileMapper;
import com.recruitmatch.mapper.ResumeFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class ResumeFileService extends ServiceImpl<ResumeFileMapper, ResumeFile> {
    @Autowired
    private CandidateProfileMapper candidateProfileMapper;

    public Page<ResumeFile> pageByRole(Integer pageNum, Integer pageSize, String keyword, Long candidateId, Integer parseStatus, Long userId, String role) {
        LambdaQueryWrapper<ResumeFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(ResumeFile::getFileName, keyword).or().like(ResumeFile::getSkills, keyword));
        wrapper.eq(parseStatus != null, ResumeFile::getParseStatus, parseStatus);
        if ("CANDIDATE".equals(role)) {
            CandidateProfile profile = getOwnProfile(userId);
            wrapper.eq(ResumeFile::getCandidateId, profile.getId());
        } else {
            wrapper.eq(candidateId != null, ResumeFile::getCandidateId, candidateId);
        }
        wrapper.orderByDesc(ResumeFile::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(ResumeFile entity, Long currentUserId, String role) {
        if ("CANDIDATE".equals(role)) {
            CandidateProfile profile = getOwnProfile(currentUserId);
            if (entity.getId() != null) {
                ResumeFile db = getById(entity.getId());
                if (db == null) {
                    throw new BusinessException(400, "简历材料不存在");
                }
                ensureOwnedCandidate(db.getCandidateId(), profile.getId());
                entity.setCreateTime(db.getCreateTime());
                entity.setParseStatus(db.getParseStatus());
            } else {
                entity.setCreateTime(LocalDateTime.now());
                entity.setParseStatus(0);
            }
            entity.setCandidateId(profile.getId());
        } else if (entity.getId() == null) {
            entity.setCreateTime(LocalDateTime.now());
            entity.setParseStatus(entity.getParseStatus() == null ? 0 : entity.getParseStatus());
        } else {
            ResumeFile db = getById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "简历材料不存在");
            }
            entity.setCreateTime(db.getCreateTime());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void deleteByRole(Long id, Long currentUserId, String role) {
        ResumeFile entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "简历材料不存在");
        }
        if ("CANDIDATE".equals(role)) {
            CandidateProfile profile = getOwnProfile(currentUserId);
            ensureOwnedCandidate(entity.getCandidateId(), profile.getId());
        }
        removeById(id);
    }

    private CandidateProfile getOwnProfile(Long userId) {
        CandidateProfile profile = candidateProfileMapper.selectOne(new LambdaQueryWrapper<CandidateProfile>().eq(CandidateProfile::getUserId, userId).last("limit 1"));
        if (profile == null) {
            throw new BusinessException(400, "请先创建候选人档案");
        }
        return profile;
    }

    private void ensureOwnedCandidate(Long candidateId, Long ownCandidateId) {
        if (!ownCandidateId.equals(candidateId)) {
            throw new BusinessException(403, "只能维护自己的简历材料");
        }
    }
}
