package com.xiaou.health.repository;

import com.xiaou.health.entity.HealthAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HealthAlertRepository extends JpaRepository<HealthAlert, Long> {
    List<HealthAlert> findByPatientIdOrderByCreateTimeDesc(Long patientId);
    List<HealthAlert> findByPatientIdAndIsRead(Long patientId, Integer isRead);
}
