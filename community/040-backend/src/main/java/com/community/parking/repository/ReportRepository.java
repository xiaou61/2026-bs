package com.community.parking.repository;

import com.community.parking.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>, QuerydslPredicateExecutor<Report> {
    Optional<Report> findByReportNo(String reportNo);
    
    List<Report> findByReporterIdOrderByCreatedAtDesc(Long reporterId);
    
    List<Report> findByStatusOrderByCreatedAtDesc(String status);
    
    long countByStatus(String status);
}
