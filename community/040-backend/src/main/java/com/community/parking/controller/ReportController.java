package com.community.parking.controller;

import com.community.parking.common.Result;
import com.community.parking.dto.ReportDTO;
import com.community.parking.entity.Report;
import com.community.parking.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public Result<Report> submitReport(@RequestHeader("userId") Long userId, @RequestBody ReportDTO dto) {
        try {
            Report report = reportService.submitReport(userId, dto);
            return Result.success(report);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/my")
    public Result<List<Report>> getMyReports(@RequestHeader("userId") Long userId) {
        try {
            List<Report> reports = reportService.getMyReports(userId);
            return Result.success(reports);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/pending")
    public Result<List<Report>> getPendingReports() {
        try {
            List<Report> reports = reportService.getPendingReports();
            return Result.success(reports);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<Report> getReportById(@PathVariable Long id) {
        try {
            Report report = reportService.getReportById(id);
            return Result.success(report);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/{id}/audit")
    public Result<Report> auditReport(
            @PathVariable Long id,
            @RequestHeader("userId") Long userId,
            @RequestParam String status,
            @RequestParam(required = false) String reason) {
        try {
            Report report = reportService.auditReport(id, userId, status, reason);
            return Result.success(report);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
