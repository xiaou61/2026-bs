package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("health_record")
public class HealthRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long elderId;
    private LocalDate recordDate;
    private String bloodPressure;
    private Integer heartRate;
    private BigDecimal temperature;
    private BigDecimal bloodSugar;
    private BigDecimal weight;
    private BigDecimal sleepHours;
    private Integer appetite;  // 1-差 2-一般 3-好
    private Integer mentalState;  // 1-差 2-一般 3-好
    private String medication;
    private String symptoms;
    private Long recorderId;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
