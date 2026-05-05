package com.carbonmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("emission_factor")
public class EmissionFactor {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String factorNo;
    private String factorName;
    private String energyType;
    private BigDecimal factorValue;
    private String unitName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
