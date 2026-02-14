package com.student.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Activity {
    private Long id;
    private String title;
    private Long organizerId;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer maxParticipant;
    private Integer participantCount;
    private Integer status;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
