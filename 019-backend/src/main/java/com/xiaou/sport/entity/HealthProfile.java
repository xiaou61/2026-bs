package com.xiaou.sport.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("health_profile")
public class HealthProfile {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate recordDate;

    private BigDecimal weight;

    private BigDecimal bmi;

    private BigDecimal bodyFat;

    private BigDecimal muscleMass;

    private Integer waterIntake;

    private BigDecimal sleepHours;

    private Integer dietCalories;

    private String dietRecord;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
