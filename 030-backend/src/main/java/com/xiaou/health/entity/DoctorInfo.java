package com.xiaou.health.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "doctor_info")
public class DoctorInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(length = 50)
    private String title;

    @Column(length = 100)
    private String hospital;

    @Column(length = 100)
    private String department;

    @Column(length = 50)
    private String specialty;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(length = 50)
    private String licenseNumber;

    private Integer yearsOfExperience;

    @Column(nullable = false)
    private Integer consultationFee = 0;

    @Column(nullable = false)
    private Integer verifyStatus = 0;

    @Column(updatable = false)
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
