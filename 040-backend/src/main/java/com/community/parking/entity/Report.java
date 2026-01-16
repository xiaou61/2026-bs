package com.community.parking.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String reportNo;

    @Column(nullable = false)
    private Long reporterId;

    private Long vehicleId;

    @Column(length = 20)
    private String plateNumber;

    @Column(nullable = false)
    private Long violationTypeId;

    @Column(length = 500)
    private String location;

    @Column(precision = 10, scale = 6)
    private Double longitude;

    @Column(precision = 10, scale = 6)
    private Double latitude;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String images;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isAnonymous;

    @Column(length = 20)
    private String status;

    private Long auditUserId;

    private LocalDateTime auditTime;

    @Column(length = 500)
    private String auditReason;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = "PENDING";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
