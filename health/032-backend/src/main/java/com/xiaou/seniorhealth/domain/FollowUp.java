package com.xiaou.seniorhealth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("follow_up")
public class FollowUp {
    @Id
    @Column("id")
    private Long id;
    @Column("elder_id")
    private Long elderId;
    @Column("doctor_user_id")
    private Long doctorUserId;
    @Column("type")
    private String type;
    @Column("due_date")
    private LocalDate dueDate;
    @Column("note")
    private String note;
    @Column("status")
    private String status;
    @Column("created_at")
    private LocalDateTime createdAt;
}
