package com.xiaou.health.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "health_assessments")
public class HealthAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(precision = 5, scale = 2)
    private BigDecimal healthScore;

    @Column(precision = 5, scale = 2)
    private BigDecimal bmi;

    @Column(length = 50)
    private String bmiLevel;

    @Column(length = 50)
    private String bloodPressureLevel;

    @Column(length = 50)
    private String bloodSugarLevel;

    @Column(columnDefinition = "TEXT")
    private String riskFactors;

    @Column(columnDefinition = "TEXT")
    private String suggestions;

    private LocalDateTime assessmentDate;

    @Column(updatable = false)
    private LocalDateTime createTime;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }
}
