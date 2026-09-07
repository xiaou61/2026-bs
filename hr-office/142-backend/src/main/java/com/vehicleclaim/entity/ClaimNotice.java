package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClaimNotice {
    private Long id;
    private String noticeNo;
    private String reportNo;
    private String noticeType;
    private String noticeContent;
    private String receiverName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
