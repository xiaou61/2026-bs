package com.xiaou.health.service;

import com.xiaou.health.entity.DoctorInfo;
import com.xiaou.health.repository.DoctorInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DoctorInfoService {
    private final DoctorInfoRepository doctorInfoRepository;

    public DoctorInfoService(DoctorInfoRepository doctorInfoRepository) {
        this.doctorInfoRepository = doctorInfoRepository;
    }

    @Transactional
    public DoctorInfo createOrUpdateDoctorInfo(Long userId, DoctorInfo doctorInfo) {
        DoctorInfo existing = doctorInfoRepository.findByUserId(userId).orElse(null);
        
        if (existing != null) {
            existing.setTitle(doctorInfo.getTitle());
            existing.setHospital(doctorInfo.getHospital());
            existing.setDepartment(doctorInfo.getDepartment());
            existing.setSpecialty(doctorInfo.getSpecialty());
            existing.setIntroduction(doctorInfo.getIntroduction());
            existing.setLicenseNumber(doctorInfo.getLicenseNumber());
            existing.setYearsOfExperience(doctorInfo.getYearsOfExperience());
            existing.setConsultationFee(doctorInfo.getConsultationFee());
            return doctorInfoRepository.save(existing);
        }
        
        doctorInfo.setUserId(userId);
        doctorInfo.setVerifyStatus(0);
        return doctorInfoRepository.save(doctorInfo);
    }

    public DoctorInfo getDoctorInfo(Long userId) {
        return doctorInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("医生信息不存在"));
    }

    public List<DoctorInfo> getVerifiedDoctors() {
        return doctorInfoRepository.findByVerifyStatus(1);
    }

    public List<DoctorInfo> getPendingDoctors() {
        return doctorInfoRepository.findByVerifyStatus(0);
    }

    @Transactional
    public DoctorInfo verifyDoctor(Long id, Integer status) {
        DoctorInfo doctorInfo = doctorInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("医生信息不存在"));
        
        doctorInfo.setVerifyStatus(status);
        return doctorInfoRepository.save(doctorInfo);
    }
}
