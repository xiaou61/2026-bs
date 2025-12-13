package com.xiaou.health.repository;

import com.xiaou.health.entity.HealthAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HealthAssessmentRepository extends JpaRepository<HealthAssessment, Long> {
    List<HealthAssessment> findByPatientIdOrderByAssessmentDateDesc(Long patientId);
}
