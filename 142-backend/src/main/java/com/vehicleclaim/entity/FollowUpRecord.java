package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FollowUpRecord {
    private Long id;
    private String followupNo;
    private String reportNo;
    private String followupMethod;
    private String followupContent;
    private String customerFeedback;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
