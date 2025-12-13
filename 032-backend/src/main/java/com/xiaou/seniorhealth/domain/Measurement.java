package com.xiaou.seniorhealth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("measurement")
public class Measurement {
    @Id
    private Long id;
    private Long elderId;
    private String type;
    private Double value1;
    private Double value2;
    private String unit;
    private LocalDateTime measuredAt;
    private LocalDateTime createdAt;
}
