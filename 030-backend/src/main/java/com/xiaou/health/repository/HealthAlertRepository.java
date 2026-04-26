package com.xiaou.health.repository;

import com.xiaou.health.entity.HealthAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HealthAlertRepository extends JpaRepository<HealthAlert, Long> {
    List<HealthAlert> findByPatientIdOrderByCreateTimeDesc(Long patientId);
    List<HealthAlert> findByPatientIdAndIsRead(Long patientId, Integer isRead);
    long countByPatientIdAndIsRead(Long patientId, Integer isRead);
    Optional<HealthAlert> findTopByPatientIdAndAlertTypeOrderByCreateTimeDesc(Long patientId, String alertType);
    long countByIsRead(Integer isRead);
}
