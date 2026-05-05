package com.carbonmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("emission_record")
public class EmissionRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String emissionNo;
    private String companyNo;
    private String scopeType;
    private BigDecimal carbonAmount;
    private String sourceName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
