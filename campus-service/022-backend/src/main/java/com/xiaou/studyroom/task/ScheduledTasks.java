package com.xiaou.studyroom.task;

import com.xiaou.studyroom.service.ReservationService;
import com.xiaou.studyroom.service.UsageStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UsageStatisticsService usageStatisticsService;

    /**
     * 每5分钟检查一次超时预约并释放座位
     */
    @Scheduled(fixedRate = 5 * 60 * 1000) // 5分钟
    public void releaseExpiredReservations() {
        logger.info("开始检查超时预约...");
        try {
            reservationService.releaseExpiredReservations();
            logger.info("超时预约检查完成");
        } catch (Exception e) {
            logger.error("释放超时预约时发生错误", e);
        }
    }

    /**
     * 每小时统计一次使用数据
     */
    @Scheduled(fixedRate = 60 * 60 * 1000) // 1小时
    public void generateUsageStatistics() {
        logger.info("开始生成使用统计数据...");
        try {
            usageStatisticsService.generateDailyStatistics();
            logger.info("使用统计数据生成完成");
        } catch (Exception e) {
            logger.error("生成使用统计数据时发生错误", e);
        }
    }

    /**
     * 每天凌晨1点执行日统计
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void dailyStatisticsTask() {
        logger.info("开始执行每日统计任务...");
        try {
            usageStatisticsService.generateDailyReport();
            logger.info("每日统计任务完成");
        } catch (Exception e) {
            logger.error("执行每日统计任务时发生错误", e);
        }
    }
}