package com.xiaou.seniorhealth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("elder")
public class Elder {
    @Id
    private Long id;
    private Long userId;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String phone;
    private String address;
    private String emergencyContact;
    private LocalDateTime createdAt;
}
