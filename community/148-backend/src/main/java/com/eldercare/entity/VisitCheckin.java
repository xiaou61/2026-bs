package com.eldercare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitCheckin {
    private Long id;
    private String checkinNo;
    private String elderName;
    private String checkinType;
    private String checkinRemark;
    private String checkinTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
