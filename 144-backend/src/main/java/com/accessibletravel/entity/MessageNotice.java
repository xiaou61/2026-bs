package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageNotice {
    private Long id;
    private String noticeNo;
    private String travelerNo;
    private String noticeType;
    private String noticeContent;
    private String receiverName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

