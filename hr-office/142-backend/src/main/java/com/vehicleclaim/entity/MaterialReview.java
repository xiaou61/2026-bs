package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MaterialReview {
    private Long id;
    private String reviewNo;
    private String reportNo;
    private String reviewResult;
    private String reviewTime;
    private String reviewerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
