package com.carbonmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("verification_report")
public class VerificationReport {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String reportNo;
    private String companyNo;
    private String reportMonth;
    private String auditorName;
    private String conclusionText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
