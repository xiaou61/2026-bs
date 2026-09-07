package com.xiaou.health.repository;

import com.xiaou.health.entity.PatientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientInfoRepository extends JpaRepository<PatientInfo, Long> {
    Optional<PatientInfo> findByUserId(Long userId);
}
