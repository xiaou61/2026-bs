package com.xiaou.seniorhealth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("notification")
public class Notification {
    @Id
    @Column("id")
    private Long id;
    @Column("user_id")
    private Long userId;
    @Column("title")
    private String title;
    @Column("content")
    private String content;
    @Column("status")
    private String status;
    @Column("created_at")
    private LocalDateTime createdAt;
}
