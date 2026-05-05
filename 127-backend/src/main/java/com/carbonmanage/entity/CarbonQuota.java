package com.carbonmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("carbon_quota")
public class CarbonQuota {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String quotaNo;
    private String companyNo;
    private Integer quotaYear;
    private BigDecimal quotaAmount;
    private BigDecimal usedAmount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
