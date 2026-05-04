package com.recruitmatch.service;

import com.recruitmatch.entity.CertificateRecord;
import com.recruitmatch.mapper.CertificateRecordMapper;
import com.recruitmatch.common.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class CertificateRecordService extends ServiceImpl<CertificateRecordMapper, CertificateRecord> {
    public Page<CertificateRecord> page(Integer pageNum, Integer pageSize, String keyword, Long candidateId, Integer verifyStatus) {
        LambdaQueryWrapper<CertificateRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(CertificateRecord::getCertName, keyword).or().like(CertificateRecord::getIssueOrg, keyword));
        wrapper.eq(candidateId != null, CertificateRecord::getCandidateId, candidateId);
        wrapper.eq(verifyStatus != null, CertificateRecord::getVerifyStatus, verifyStatus);
        wrapper.orderByDesc(CertificateRecord::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(CertificateRecord entity) {
        if (entity.getId() == null) {
            entity.setVerifyStatus(entity.getVerifyStatus() == null ? 0 : entity.getVerifyStatus());
            entity.setCreateTime(LocalDateTime.now());
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
}
