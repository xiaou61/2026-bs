package com.psychologycare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("risk_assessment")
public class RiskAssessment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String assessmentNo;
    private String caseTheme;
    private String assessorName;
    private String riskLevel;
    private String assessmentTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}







