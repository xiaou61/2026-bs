package com.xiaou.seniorhealth.repository;

import com.xiaou.seniorhealth.domain.FollowUp;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FollowUpRepository extends CrudRepository<FollowUp, Long> {
    @Query("select * from follow_up where elder_id = :elderId order by due_date asc")
    List<FollowUp> findByElder(@Param("elderId") Long elderId);
    @Query("select * from follow_up where doctor_user_id = :doctor and due_date between :from and :to order by due_date asc")
    List<FollowUp> findRangeByDoctor(@Param("doctor") Long doctor, @Param("from") LocalDate from, @Param("to") LocalDate to);
    @Query("select count(*) from follow_up where status = 'PENDING' and due_date between :from and :to")
    long countPendingInRange(@Param("from") LocalDate from, @Param("to") LocalDate to);
    @Query("select * from follow_up where status = 'PENDING' and due_date between :from and :to")
    List<FollowUp> findDueAll(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
