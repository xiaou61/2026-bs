package com.datamasking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("risk_alert")
public class RiskAlert {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String alertNo;
    private String datasetName;
    private String alertType;
    private String riskLevel;
    private String ownerName;
    private LocalDateTime detectedTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
