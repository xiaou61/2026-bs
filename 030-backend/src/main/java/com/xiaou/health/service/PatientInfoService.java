package com.xiaou.health.service;

import com.xiaou.health.entity.PatientInfo;
import com.xiaou.health.repository.PatientInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientInfoService {
    private final PatientInfoRepository patientInfoRepository;

    public PatientInfoService(PatientInfoRepository patientInfoRepository) {
        this.patientInfoRepository = patientInfoRepository;
    }

    @Transactional
    public PatientInfo createOrUpdatePatientInfo(Long userId, PatientInfo patientInfo) {
        PatientInfo existing = patientInfoRepository.findByUserId(userId).orElse(null);
        
        if (existing != null) {
            existing.setAddress(patientInfo.getAddress());
            existing.setMedicalHistory(patientInfo.getMedicalHistory());
            existing.setFamilyHistory(patientInfo.getFamilyHistory());
            existing.setAllergyHistory(patientInfo.getAllergyHistory());
            existing.setSurgeryHistory(patientInfo.getSurgeryHistory());
            existing.setLifestyle(patientInfo.getLifestyle());
            existing.setSmokingStatus(patientInfo.getSmokingStatus());
            existing.setDrinkingStatus(patientInfo.getDrinkingStatus());
            return patientInfoRepository.save(existing);
        }
        
        patientInfo.setUserId(userId);
        return patientInfoRepository.save(patientInfo);
    }

    public PatientInfo getPatientInfo(Long userId) {
        return patientInfoRepository.findByUserId(userId).orElse(null);
    }
}
