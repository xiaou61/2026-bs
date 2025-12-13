package com.xiaou.seniorhealth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("follow_up")
public class FollowUp {
    @Id
    private Long id;
    private Long elderId;
    private Long doctorUserId;
    private String type;
    private LocalDate dueDate;
    private String note;
    private String status;
    private LocalDateTime createdAt;
}
