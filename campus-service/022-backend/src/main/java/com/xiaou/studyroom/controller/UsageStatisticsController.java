package com.xiaou.studyroom.controller;

import com.xiaou.studyroom.common.Result;
import com.xiaou.studyroom.entity.UsageStatistics;
import com.xiaou.studyroom.service.UsageStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin
public class UsageStatisticsController {

    @Autowired
    private UsageStatisticsService usageStatisticsService;

    @GetMapping("/date-range")
    public Result<List<UsageStatistics>> getStatisticsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<UsageStatistics> statistics = usageStatisticsService.getStatisticsByDateRange(startDate, endDate);
        return Result.success(statistics);
    }

    @GetMapping("/room/{roomId}")
    public Result<Map<String, Object>> getRoomUsageStatistics(
            @PathVariable Long roomId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Map<String, Object> statistics = usageStatisticsService.getRoomUsageStatistics(roomId, startDate, endDate);
        return Result.success(statistics);
    }

    @GetMapping("/overall")
    public Result<Map<String, Object>> getOverallStatistics(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Map<String, Object> statistics = usageStatisticsService.getOverallStatistics(startDate, endDate);
        return Result.success(statistics);
    }

    @GetMapping("/peak-hours/{roomId}")
    public Result<List<Map<String, Object>>> getPeakHoursStatistics(
            @PathVariable Long roomId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Map<String, Object>> peakHours = usageStatisticsService.getPeakHoursStatistics(roomId, date);
        return Result.success(peakHours);
    }

    @GetMapping("/usage-rate/{roomId}")
    public Result<Double> getUsageRate(
            @PathVariable Long roomId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        double usageRate = usageStatisticsService.calculateUsageRate(roomId, date);
        return Result.success(usageRate);
    }

    @PostMapping("/generate")
    public Result<String> generateStatistics() {
        try {
            usageStatisticsService.generateDailyReport();
            return Result.success("统计数据生成成功");
        } catch (Exception e) {
            return Result.error("统计数据生成失败：" + e.getMessage());
        }
    }

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> getDashboardData() {
        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(7);
        LocalDate monthAgo = today.minusDays(30);

        Map<String, Object> dashboard = Map.of(
            "today", usageStatisticsService.getOverallStatistics(today, today),
            "week", usageStatisticsService.getOverallStatistics(weekAgo, today),
            "month", usageStatisticsService.getOverallStatistics(monthAgo, today)
        );

        return Result.success(dashboard);
    }
}