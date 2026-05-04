package com.phishingtraining.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("risk_score")
public class RiskScore {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String employeeName;
    private String departmentName;
    private Integer clickCount;
    private Integer examScore;
    private Integer riskScore;
    private String riskLevel;
    private String reviewerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
