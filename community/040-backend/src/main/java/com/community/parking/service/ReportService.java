package com.community.parking.service;

import com.community.parking.dto.ReportDTO;
import com.community.parking.entity.Report;
import com.community.parking.repository.ReportRepository;
import com.community.parking.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public Report submitReport(Long reporterId, ReportDTO dto) {
        Report report = new Report();
        report.setReportNo(generateReportNo());
        report.setReporterId(reporterId);
        report.setPlateNumber(dto.getPlateNumber());
        report.setViolationTypeId(dto.getViolationTypeId());
        report.setLocation(dto.getLocation());
        report.setLongitude(dto.getLongitude());
        report.setLatitude(dto.getLatitude());
        report.setDescription(dto.getDescription());
        report.setImages(dto.getImages());
        report.setIsAnonymous(dto.getIsAnonymous() != null && dto.getIsAnonymous());
        report.setStatus("PENDING");

        vehicleRepository.findByPlateNumber(dto.getPlateNumber())
                .ifPresent(vehicle -> report.setVehicleId(vehicle.getId()));

        return reportRepository.save(report);
    }

    public List<Report> getMyReports(Long userId) {
        return reportRepository.findByReporterIdOrderByCreatedAtDesc(userId);
    }

    public List<Report> getPendingReports() {
        return reportRepository.findByStatusOrderByCreatedAtDesc("PENDING");
    }

    public Report getReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("举报不存在"));
    }

    @Transactional
    public Report auditReport(Long reportId, Long auditUserId, String status, String reason) {
        Report report = getReportById(reportId);
        report.setStatus(status);
        report.setAuditUserId(auditUserId);
        report.setAuditTime(LocalDateTime.now());
        report.setAuditReason(reason);
        return reportRepository.save(report);
    }

    private String generateReportNo() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = reportRepository.count() + 1;
        return String.format("WP%s%03d", date, count);
    }
}
