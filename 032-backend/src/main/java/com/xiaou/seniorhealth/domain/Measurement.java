package com.xiaou.seniorhealth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("measurement")
public class Measurement {
    @Id
    @Column("id")
    private Long id;
    @Column("elder_id")
    private Long elderId;
    @Column("type")
    private String type;
    @Column("value1")
    private Double value1;
    @Column("value2")
    private Double value2;
    @Column("unit")
    private String unit;
    @Column("measured_at")
    private LocalDateTime measuredAt;
    @Column("created_at")
    private LocalDateTime createdAt;
}
