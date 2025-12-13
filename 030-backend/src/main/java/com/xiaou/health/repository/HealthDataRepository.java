package com.xiaou.health.repository;

import com.xiaou.health.entity.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HealthDataRepository extends JpaRepository<HealthData, Long> {
    List<HealthData> findByPatientIdOrderByMeasureTimeDesc(Long patientId);
    List<HealthData> findByPatientIdAndDataType(Long patientId, String dataType);
    List<HealthData> findByPatientIdAndMeasureTimeBetween(Long patientId, LocalDateTime start, LocalDateTime end);
}
