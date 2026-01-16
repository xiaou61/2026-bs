package com.community.parking.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appeals")
public class Appeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reportId;

    @Column(nullable = false)
    private Long userId;

    @Column(length = 100)
    private String appealReason;

    @Column(length = 1000)
    private String appealDescription;

    @Column(length = 1000)
    private String appealImages;

    @Column(length = 20)
    private String status;

    private Long auditUserId;

    private LocalDateTime auditTime;

    @Column(length = 500)
    private String auditResult;

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
