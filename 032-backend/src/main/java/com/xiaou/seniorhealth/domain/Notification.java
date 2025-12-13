package com.xiaou.seniorhealth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("notification")
public class Notification {
    @Id
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String status;
    private LocalDateTime createdAt;
}
