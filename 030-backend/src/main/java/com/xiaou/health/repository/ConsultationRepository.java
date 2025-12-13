package com.xiaou.health.repository;

import com.xiaou.health.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByPatientIdOrderByCreateTimeDesc(Long patientId);
    List<Consultation> findByDoctorIdOrderByCreateTimeDesc(Long doctorId);
    List<Consultation> findByDoctorIdAndStatus(Long doctorId, String status);
}
