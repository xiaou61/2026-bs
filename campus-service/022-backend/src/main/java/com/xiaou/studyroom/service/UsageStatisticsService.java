package com.xiaou.studyroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.studyroom.entity.UsageStatistics;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UsageStatisticsService extends IService<UsageStatistics> {

    void generateDailyStatistics();

    void generateDailyReport();

    List<UsageStatistics> getStatisticsByDateRange(LocalDate startDate, LocalDate endDate);

    Map<String, Object> getRoomUsageStatistics(Long roomId, LocalDate startDate, LocalDate endDate);

    Map<String, Object> getOverallStatistics(LocalDate startDate, LocalDate endDate);

    List<Map<String, Object>> getPeakHoursStatistics(Long roomId, LocalDate date);

    double calculateUsageRate(Long roomId, LocalDate date);
}