package com.xiaou.seniorhealth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("elder")
public class Elder {
    @Id
    @Column("id")
    private Long id;
    @Column("user_id")
    private Long userId;
    @Column("name")
    private String name;
    @Column("gender")
    private String gender;
    @Column("birth_date")
    private LocalDate birthDate;
    @Column("phone")
    private String phone;
    @Column("address")
    private String address;
    @Column("emergency_contact")
    private String emergencyContact;
    @Column("created_at")
    private LocalDateTime createdAt;
}
