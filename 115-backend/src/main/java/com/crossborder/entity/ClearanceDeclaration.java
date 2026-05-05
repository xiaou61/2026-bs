package com.crossborder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("clearance_declaration")
public class ClearanceDeclaration {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String declarationNo;
    private String orderNo;
    private String customsPort;
    private String declareType;
    private BigDecimal declareAmount;
    private String riskLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
