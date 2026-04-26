package com.hospital.service;

import com.hospital.common.BusinessException;
import com.hospital.entity.MedicalCard;
import com.hospital.mapper.MedicalCardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CardService {

    @Autowired
    private MedicalCardMapper medicalCardMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    public List<MedicalCard> myCards(Long userId) {
        return medicalCardMapper.selectByUserId(userId);
    }

    public void save(MedicalCard entity, Long userId, String role) {
        authService.assertPatient(role);
        if (entity == null || !StringUtils.hasText(entity.getPatientName()) || !StringUtils.hasText(entity.getPhone())) {
            throw new BusinessException("持卡人和手机号不能为空");
        }
        if (entity.getId() == null) {
            entity.setUserId(userId);
            entity.setCardNo(generateCardNo());
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setIsDefault(medicalCardMapper.selectDefaultByUserId(userId) == null ? 1 : 0);
            entity.setCreateTime(LocalDateTime.now());
            if (entity.getIsDefault() == 1) {
                medicalCardMapper.clearDefault(userId);
            }
            medicalCardMapper.insert(entity);
            operationLogService.record(userId, role, "就诊卡", "新增", "新增就诊卡");
            return;
        }
        MedicalCard db = medicalCardMapper.selectById(entity.getId());
        if (db == null || !db.getUserId().equals(userId)) {
            throw new BusinessException("就诊卡不存在");
        }
        db.setPatientName(entity.getPatientName());
        db.setPhone(entity.getPhone());
        db.setIdCard(entity.getIdCard());
        db.setStatus(entity.getStatus() == null ? db.getStatus() : entity.getStatus());
        if (entity.getIsDefault() != null && entity.getIsDefault() == 1) {
            medicalCardMapper.clearDefault(userId);
            db.setIsDefault(1);
        }
        medicalCardMapper.update(db);
        operationLogService.record(userId, role, "就诊卡", "编辑", "编辑就诊卡");
    }

    public void setDefault(Long id, Long userId, String role) {
        authService.assertPatient(role);
        MedicalCard db = medicalCardMapper.selectById(id);
        if (db == null || !db.getUserId().equals(userId)) {
            throw new BusinessException("就诊卡不存在");
        }
        medicalCardMapper.clearDefault(userId);
        db.setIsDefault(1);
        medicalCardMapper.update(db);
        operationLogService.record(userId, role, "就诊卡", "设为默认", "切换默认就诊卡");
    }

    public void delete(Long id, Long userId, String role) {
        authService.assertPatient(role);
        medicalCardMapper.deleteById(id, userId);
        operationLogService.record(userId, role, "就诊卡", "删除", "删除就诊卡");
    }

    public MedicalCard getOwnedCard(Long id, Long userId) {
        MedicalCard card = medicalCardMapper.selectById(id);
        if (card == null || !card.getUserId().equals(userId)) {
            throw new BusinessException("就诊卡不存在");
        }
        if (card.getStatus() == null || card.getStatus() != 1) {
            throw new BusinessException("就诊卡不可用");
        }
        return card;
    }

    public long countByUserId(Long userId) {
        return medicalCardMapper.countByUserId(userId);
    }

    private String generateCardNo() {
        return "CARD" + System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(100, 999);
    }
}
