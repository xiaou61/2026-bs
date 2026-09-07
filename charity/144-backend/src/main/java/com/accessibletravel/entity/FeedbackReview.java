package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FeedbackReview {
    private Long id;
    private String feedbackNo;
    private String volunteerNo;
    private Integer satisfactionLevel;
    private String feedbackTime;
    private String reviewTarget;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

