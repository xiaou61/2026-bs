package com.xiaou.seniorhealth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("appointment")
public class Appointment {
    @Id
    @Column("id")
    private Long id;
    @Column("elder_id")
    private Long elderId;
    @Column("doctor_user_id")
    private Long doctorUserId;
    @Column("start_time")
    private LocalDateTime startTime;
    @Column("reason")
    private String reason;
    @Column("status")
    private String status;
    @Column("created_at")
    private LocalDateTime createdAt;
}
