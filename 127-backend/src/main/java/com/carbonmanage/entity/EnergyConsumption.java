package com.carbonmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("energy_consumption")
public class EnergyConsumption {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String recordNo;
    private String companyNo;
    private String energyType;
    private BigDecimal amountValue;
    private String unitName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
