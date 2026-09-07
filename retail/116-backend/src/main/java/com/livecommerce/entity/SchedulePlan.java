package com.livecommerce.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SchedulePlan {
    private Long id;
    private String planNo;
    private String sessionTitle;
    private String planDate;
    private String anchorName;
    private Integer productCount;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
