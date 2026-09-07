package com.diet.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 健康数据实体
 */
@Data
@TableName("health_data")
public class HealthData implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private LocalDate recordDate;
    
    private BigDecimal weight;
    
    private BigDecimal bmi;
    
    private BigDecimal bodyFatRate;
    
    private BigDecimal muscleMass;
    
    private BigDecimal basalMetabolism;
    
    private Integer bloodPressureHigh;
    
    private Integer bloodPressureLow;
    
    private Integer heartRate;
    
    private BigDecimal sleepHours;
    
    private Integer exerciseMinutes;
    
    private String remark;
    
    @TableLogic
    private Integer deleted;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
