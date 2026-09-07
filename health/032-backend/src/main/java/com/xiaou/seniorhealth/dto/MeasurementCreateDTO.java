package com.xiaou.seniorhealth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeasurementCreateDTO {
    @NotNull
    private Long elderId;
    @NotNull
    private String type;
    private Double value1;
    private Double value2;
    private String unit;
    @NotNull
    private LocalDateTime measuredAt;
}
