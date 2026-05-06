package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FollowUpRecord {
    private Long id;
    private String statNo;
    private String projectNo;
    private String statMonth;
    private Integer claimCount;
    private Integer achievementCount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}




