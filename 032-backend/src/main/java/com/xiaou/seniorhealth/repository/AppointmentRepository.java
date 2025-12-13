package com.xiaou.seniorhealth.repository;

import com.xiaou.seniorhealth.domain.Appointment;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    @Query("select * from appointment where elder_id = :elderId order by start_time desc")
    List<Appointment> findByElder(@Param("elderId") Long elderId);
    @Query("select * from appointment where doctor_user_id = :doctor and start_time >= :from and status = 'SCHEDULED' order by start_time asc")
    List<Appointment> findUpcomingByDoctor(@Param("doctor") Long doctor, @Param("from") LocalDateTime from);
    @Query("select count(*) from appointment where start_time >= :from and status = 'SCHEDULED'")
    long countUpcoming(@Param("from") LocalDateTime from);
    @Query("select * from appointment where start_time between :from and :to and status = 'SCHEDULED'")
    List<Appointment> findUpcomingAll(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
