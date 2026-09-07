package com.eldercare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CareNotice {
    private Long id;
    private String noticeNo;
    private String elderName;
    private String noticeType;
    private String noticeContent;
    private String receiverName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
