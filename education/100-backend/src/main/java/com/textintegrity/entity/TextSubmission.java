package com.textintegrity.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TextSubmission {
    private Long id;
    private Long assignmentId;
    private Long studentId;
    private String title;
    private String content;
    private String citationNote;
    private String attachmentUrl;
    private Integer wordCount;
    private Integer status;
    private LocalDateTime submitTime;
    private LocalDateTime updateTime;
}
