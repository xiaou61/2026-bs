package com.esgreport.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReviewTask {
    private Long id;
    private String reviewNo;
    private String submissionNo;
    private String reviewerName;
    private String deadlineTime;
    private String reviewOpinion;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
