package com.xiaou.campusvideo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusvideo.entity.VideoReport;
import com.xiaou.campusvideo.mapper.VideoReportMapper;
import com.xiaou.campusvideo.util.UserHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VideoReportService extends ServiceImpl<VideoReportMapper, VideoReport> {
    
    public void reportVideo(Long videoId, String reportType, String reportReason) {
        VideoReport report = new VideoReport();
        report.setUserId(UserHolder.getUserId());
        report.setVideoId(videoId);
        report.setReportType(reportType);
        report.setReportReason(reportReason);
        report.setStatus(0);
        this.save(report);
    }
    
    public void handleReport(Long reportId, Integer status, String handleResult) {
        VideoReport report = this.getById(reportId);
        report.setStatus(status);
        report.setHandleResult(handleResult);
        report.setHandleAdminId(UserHolder.getUserId());
        report.setHandleTime(LocalDateTime.now());
        this.updateById(report);
    }
}

