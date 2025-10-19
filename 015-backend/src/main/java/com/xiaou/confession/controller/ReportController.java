package com.xiaou.confession.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.confession.common.Result;
import com.xiaou.confession.entity.Report;
import com.xiaou.confession.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    
    private final ReportService reportService;
    
    @PostMapping
    public Result<Report> submitReport(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            Long reporterId = (Long) request.getAttribute("userId");
            Long targetId = Long.valueOf(params.get("targetId").toString());
            Integer targetType = (Integer) params.get("targetType");
            String reportType = (String) params.get("reportType");
            String reason = (String) params.get("reason");
            String evidence = (String) params.get("evidence");
            
            Report report = reportService.submitReport(reporterId, targetId, targetType, reportType, reason, evidence);
            return Result.success(report);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/my")
    public Result<IPage<Report>> getMyReports(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            IPage<Report> reports = reportService.getMyReports(userId, page, size);
            return Result.success(reports);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Result<Report> getReportDetail(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Report report = reportService.getReportDetail(id, userId);
            return Result.success(report);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

