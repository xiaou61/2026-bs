package com.xiaou.health.repository;

import com.xiaou.health.entity.DoctorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface DoctorInfoRepository extends JpaRepository<DoctorInfo, Long> {
    Optional<DoctorInfo> findByUserId(Long userId);
    List<DoctorInfo> findByVerifyStatus(Integer verifyStatus);
}
