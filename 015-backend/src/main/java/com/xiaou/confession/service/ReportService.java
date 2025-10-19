package com.xiaou.confession.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.confession.entity.Report;
import com.xiaou.confession.entity.User;
import com.xiaou.confession.mapper.ReportMapper;
import com.xiaou.confession.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportService {
    
    private final ReportMapper reportMapper;
    private final UserMapper userMapper;
    
    @Transactional
    public Report submitReport(Long reporterId, Long targetId, Integer targetType, 
                               String reportType, String reason, String evidence) {
        User reporter = userMapper.selectById(reporterId);
        if (reporter == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (reporter.getStatus() == 2) {
            throw new RuntimeException("您的账号已被封禁，无法提交举报");
        }
        
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Report::getReporterId, reporterId);
        wrapper.eq(Report::getTargetId, targetId);
        wrapper.eq(Report::getTargetType, targetType);
        wrapper.eq(Report::getStatus, 0);
        Long count = reportMapper.selectCount(wrapper);
        
        if (count > 0) {
            throw new RuntimeException("您已对该内容提交过举报，请等待处理");
        }
        
        Report report = new Report();
        report.setReporterId(reporterId);
        report.setTargetId(targetId);
        report.setTargetType(targetType);
        report.setReportType(reportType);
        report.setReason(reason);
        report.setEvidence(evidence);
        report.setStatus(0);
        report.setCreateTime(LocalDateTime.now());
        
        reportMapper.insert(report);
        
        return report;
    }
    
    public IPage<Report> getMyReports(Long userId, Integer page, Integer size) {
        Page<Report> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Report::getReporterId, userId);
        wrapper.orderByDesc(Report::getCreateTime);
        
        return reportMapper.selectPage(pageParam, wrapper);
    }
    
    public Report getReportDetail(Long reportId, Long userId) {
        Report report = reportMapper.selectById(reportId);
        if (report == null) {
            throw new RuntimeException("举报不存在");
        }
        
        if (!report.getReporterId().equals(userId)) {
            throw new RuntimeException("无权查看");
        }
        
        return report;
    }
}

