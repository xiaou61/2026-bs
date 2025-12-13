package com.xiaou.seniorhealth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("appointment")
public class Appointment {
    @Id
    private Long id;
    private Long elderId;
    private Long doctorUserId;
    private LocalDateTime startTime;
    private String reason;
    private String status;
    private LocalDateTime createdAt;
}
