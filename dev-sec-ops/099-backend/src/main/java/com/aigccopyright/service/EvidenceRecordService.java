package com.aigccopyright.service;

import com.aigccopyright.common.BusinessException;
import com.aigccopyright.entity.CopyrightRegister;
import com.aigccopyright.entity.EvidenceRecord;
import com.aigccopyright.mapper.CopyrightRegisterMapper;
import com.aigccopyright.mapper.EvidenceRecordMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;

@Service
public class EvidenceRecordService extends ServiceImpl<EvidenceRecordMapper, EvidenceRecord> {

    @Autowired
    private CopyrightRegisterMapper copyrightRegisterMapper;

    public Page<EvidenceRecord> page(Integer pageNum, Integer pageSize, Long registerId, Integer evidenceStatus) {
        LambdaQueryWrapper<EvidenceRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(registerId != null, EvidenceRecord::getRegisterId, registerId);
        wrapper.eq(evidenceStatus != null, EvidenceRecord::getEvidenceStatus, evidenceStatus);
        wrapper.orderByDesc(EvidenceRecord::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(EvidenceRecord entity) {
        if (entity.getId() == null) {
            entity.setEvidenceStatus(entity.getEvidenceStatus() == null ? 0 : entity.getEvidenceStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public EvidenceRecord generate(Long registerId) {
        CopyrightRegister register = copyrightRegisterMapper.selectById(registerId);
        if (register == null) {
            throw new BusinessException(400, "版权登记不存在");
        }
        if (register.getRegisterStatus() == null || register.getRegisterStatus() != 1) {
            throw new BusinessException(400, "版权登记未通过，不能生成存证");
        }
        EvidenceRecord record = new EvidenceRecord();
        record.setRegisterId(registerId);
        record.setEvidenceNo("EV" + System.currentTimeMillis());
        record.setHashValue(hash(registerId + "-" + register.getAuthorName() + "-" + register.getDeclaration()));
        record.setPlatformName("AIGC版权存证链");
        record.setEvidenceStatus(1);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        save(record);
        return record;
    }

    private String hash(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte item : bytes) {
                builder.append(String.format("%02x", item & 0xff));
            }
            return builder.toString();
        } catch (Exception e) {
            throw new BusinessException("存证哈希生成失败");
        }
    }
}
