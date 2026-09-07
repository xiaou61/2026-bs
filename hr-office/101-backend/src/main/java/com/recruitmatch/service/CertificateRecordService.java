package com.recruitmatch.service;

import com.recruitmatch.entity.CertificateRecord;
import com.recruitmatch.entity.CandidateProfile;
import com.recruitmatch.mapper.CertificateRecordMapper;
import com.recruitmatch.mapper.CandidateProfileMapper;
import com.recruitmatch.common.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class CertificateRecordService extends ServiceImpl<CertificateRecordMapper, CertificateRecord> {
    @Autowired
    private CandidateProfileMapper candidateProfileMapper;

    public Page<CertificateRecord> pageByRole(Integer pageNum, Integer pageSize, String keyword, Long candidateId, Integer verifyStatus, Long userId, String role) {
        LambdaQueryWrapper<CertificateRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(CertificateRecord::getCertName, keyword).or().like(CertificateRecord::getIssueOrg, keyword));
        wrapper.eq(verifyStatus != null, CertificateRecord::getVerifyStatus, verifyStatus);
        if ("CANDIDATE".equals(role)) {
            CandidateProfile profile = getOwnProfile(userId);
            wrapper.eq(CertificateRecord::getCandidateId, profile.getId());
        } else {
            wrapper.eq(candidateId != null, CertificateRecord::getCandidateId, candidateId);
        }
        wrapper.orderByDesc(CertificateRecord::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(CertificateRecord entity, Long currentUserId, String role) {
        if ("CANDIDATE".equals(role)) {
            CandidateProfile profile = getOwnProfile(currentUserId);
            if (entity.getId() != null) {
                CertificateRecord db = getById(entity.getId());
                if (db == null) {
                    throw new BusinessException(400, "证书材料不存在");
                }
                ensureOwnedCandidate(db.getCandidateId(), profile.getId());
                entity.setCreateTime(db.getCreateTime());
            } else {
                entity.setCreateTime(LocalDateTime.now());
            }
            entity.setCandidateId(profile.getId());
            entity.setVerifyStatus(0);
            entity.setReviewComment(null);
        } else if (entity.getId() == null) {
            entity.setVerifyStatus(entity.getVerifyStatus() == null ? 0 : entity.getVerifyStatus());
            entity.setCreateTime(LocalDateTime.now());
        } else {
            CertificateRecord db = getById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "证书材料不存在");
            }
            entity.setCreateTime(db.getCreateTime());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void verify(Long id, String comment) {
        CertificateRecord entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "证书材料不存在");
        }
        entity.setVerifyStatus(1);
        entity.setReviewComment(comment);
        entity.setUpdateTime(LocalDateTime.now());
        updateById(entity);
    }

    public void reject(Long id, String comment) {
        CertificateRecord entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "证书材料不存在");
        }
        entity.setVerifyStatus(2);
        entity.setReviewComment(comment);
        entity.setUpdateTime(LocalDateTime.now());
        updateById(entity);
    }

    public void deleteByRole(Long id, Long currentUserId, String role) {
        CertificateRecord entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "证书材料不存在");
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
            throw new BusinessException(403, "只能维护自己的证书材料");
        }
    }
}
