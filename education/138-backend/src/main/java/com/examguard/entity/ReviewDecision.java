package com.examguard.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewDecision {
    private Long id;
    private String achievementNo;
    private String projectNo;
    private String achievementName;
    private String achievementType;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


