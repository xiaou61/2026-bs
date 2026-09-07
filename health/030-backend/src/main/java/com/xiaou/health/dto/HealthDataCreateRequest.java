package com.xiaou.health.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HealthDataCreateRequest {
    @NotBlank(message = "数据类型不能为空")
    private String dataType;
    
    @NotNull(message = "数值不能为空")
    private BigDecimal value;
    
    private String unit;
    
    private String remark;
    
    private LocalDateTime measureTime;
}
