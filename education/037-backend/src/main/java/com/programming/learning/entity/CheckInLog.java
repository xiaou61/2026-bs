package com.programming.learning.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 打卡记录实体
 */
@Data
public class CheckInLog {
    /**
     * 打卡ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 打卡日期
     */
    private LocalDate checkInDate;

    /**
     * 连续打卡天数
     */
    private Integer continuousDays;

    /**
     * 今日学习时长（分钟）
     */
    private Integer studyMinutes;

    /**
     * 获得积分
     */
    private Integer scoreEarned;

    /**
     * 打卡备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
