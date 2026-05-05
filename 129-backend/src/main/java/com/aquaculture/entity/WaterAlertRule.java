package com.aquaculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("water_alert_rule")
public class WaterAlertRule {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String ruleNo;
    private String ruleName;
    private String metricName;
    private BigDecimal thresholdValue;
    private String alertLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
