package com.sparelife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("life_prediction")
public class LifePrediction {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String predictionNo;
    private String partName;
    private String equipmentName;
    private Integer remainHours;
    private BigDecimal healthScore;
    private String riskLevel;
    private String modelVersion;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
