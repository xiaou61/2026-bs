package com.xiaou.health.repository;

import com.xiaou.health.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    List<DoctorSchedule> findByDoctorIdAndWorkDateGreaterThanEqual(Long doctorId, LocalDate date);
    List<DoctorSchedule> findByDoctorIdAndWorkDate(Long doctorId, LocalDate date);
}
