package com.xiaou.studyroom.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("usage_statistics")
public class UsageStatistics {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("room_id")
    private Long roomId;

    @TableField("stat_date")
    private LocalDate statDate;

    @TableField("total_reservations")
    private Integer totalReservations;

    @TableField("actual_checkins")
    private Integer actualCheckins;

    @TableField("peak_hour")
    private LocalTime peakHour;

    @TableField("usage_rate")
    private BigDecimal usageRate;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}