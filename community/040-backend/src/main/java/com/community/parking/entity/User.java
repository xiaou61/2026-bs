package com.community.parking.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false, length = 11)
    private String phone;

    @Column(length = 50)
    private String realName;

    @Column(length = 50)
    private String roomNumber;

    @Column(length = 20)
    private String role;

    @Column(length = 20)
    private String status;

    @Column(columnDefinition = "INT DEFAULT 100")
    private Integer points;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (points == null) {
            points = 100;
        }
        if (status == null) {
            status = "ACTIVE";
        }
        if (role == null) {
            role = "USER";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
