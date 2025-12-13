package com.xiaou.health.service;

import com.xiaou.health.dto.HealthDataCreateRequest;
import com.xiaou.health.entity.HealthData;
import com.xiaou.health.repository.HealthDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HealthDataService {
    private final HealthDataRepository healthDataRepository;

    public HealthDataService(HealthDataRepository healthDataRepository) {
        this.healthDataRepository = healthDataRepository;
    }

    @Transactional
    public HealthData addHealthData(Long patientId, HealthDataCreateRequest request) {
        HealthData healthData = new HealthData();
        healthData.setPatientId(patientId);
        healthData.setDataType(request.getDataType());
        healthData.setValue(request.getValue());
        healthData.setUnit(request.getUnit());
        healthData.setRemark(request.getRemark());
        healthData.setMeasureTime(request.getMeasureTime() != null ? request.getMeasureTime() : LocalDateTime.now());
        
        return healthDataRepository.save(healthData);
    }

    public List<HealthData> getPatientHealthData(Long patientId) {
        return healthDataRepository.findByPatientIdOrderByMeasureTimeDesc(patientId);
    }

    public List<HealthData> getPatientHealthDataByType(Long patientId, String dataType) {
        return healthDataRepository.findByPatientIdAndDataType(patientId, dataType);
    }

    public List<HealthData> getPatientHealthDataByPeriod(Long patientId, LocalDateTime start, LocalDateTime end) {
        return healthDataRepository.findByPatientIdAndMeasureTimeBetween(patientId, start, end);
    }

    @Transactional
    public void deleteHealthData(Long id, Long patientId) {
        HealthData healthData = healthDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("健康数据不存在"));
        
        if (!healthData.getPatientId().equals(patientId)) {
            throw new RuntimeException("无权删除此数据");
        }
        
        healthDataRepository.delete(healthData);
    }
}
