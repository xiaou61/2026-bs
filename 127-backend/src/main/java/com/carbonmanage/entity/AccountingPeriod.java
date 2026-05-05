package com.carbonmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("accounting_period")
public class AccountingPeriod {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String periodNo;
    private String companyNo;
    private String periodMonth;
    private String boundaryScope;
    private String reviewerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
