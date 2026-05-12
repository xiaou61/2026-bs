package com.recruitmatch.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recruitmatch.common.BusinessException;
import com.recruitmatch.entity.CandidateProfile;
import com.recruitmatch.mapper.CandidateProfileMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class CandidateProfileService extends ServiceImpl<CandidateProfileMapper, CandidateProfile> {
    public Page<CandidateProfile> pageByRole(Integer pageNum, Integer pageSize, String keyword, Integer status, String targetJob, Long userId, String role) {
        LambdaQueryWrapper<CandidateProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(CandidateProfile::getRealName, keyword).or().like(CandidateProfile::getSkillTags, keyword));
        wrapper.eq(status != null, CandidateProfile::getStatus, status);
        wrapper.eq(StringUtils.hasText(targetJob), CandidateProfile::getTargetJob, targetJob);
        wrapper.eq("CANDIDATE".equals(role), CandidateProfile::getUserId, userId);
        wrapper.orderByDesc(CandidateProfile::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(CandidateProfile entity, Long currentUserId, String role) {
        if ("CANDIDATE".equals(role)) {
            CandidateProfile existed = getOne(new LambdaQueryWrapper<CandidateProfile>().eq(CandidateProfile::getUserId, currentUserId).last("limit 1"));
            if (entity.getId() == null && existed != null) {
                throw new BusinessException(400, "当前账号已存在候选人档案");
            }
            if (entity.getId() != null) {
                CandidateProfile db = getById(entity.getId());
                if (db == null) {
                    throw new BusinessException(400, "候选人档案不存在");
                }
                if (!currentUserId.equals(db.getUserId())) {
                    throw new BusinessException(403, "只能维护自己的候选人档案");
                }
                entity.setCreateTime(db.getCreateTime());
                entity.setStatus(db.getStatus());
            } else {
                entity.setCreateTime(LocalDateTime.now());
                entity.setStatus(1);
            }
            entity.setUserId(currentUserId);
        } else if (entity.getId() == null) {
            entity.setCreateTime(LocalDateTime.now());
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
        } else {
            CandidateProfile db = getById(entity.getId());
            if (db == null) {
                throw new BusinessException(400, "候选人档案不存在");
            }
            entity.setCreateTime(db.getCreateTime());
            entity.setStatus(entity.getStatus() == null ? db.getStatus() : entity.getStatus());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void deleteByRole(Long id, Long currentUserId, String role) {
        CandidateProfile entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "候选人档案不存在");
        }
        if ("CANDIDATE".equals(role) && !currentUserId.equals(entity.getUserId())) {
            throw new BusinessException(403, "只能删除自己的候选人档案");
        }
        removeById(id);
    }

    public CandidateProfile getByUserId(Long userId) {
        return getOne(new LambdaQueryWrapper<CandidateProfile>().eq(CandidateProfile::getUserId, userId).last("limit 1"));
    }
}
