package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InspectionNotice {
    private Long id;
    private String noticeNo;
    private String foodName;
    private String noticeType;
    private String noticeContent;
    private String receiverName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






