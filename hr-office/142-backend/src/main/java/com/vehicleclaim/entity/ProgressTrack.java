package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProgressTrack {
    private Long id;
    private String trackNo;
    private String reportNo;
    private String currentNode;
    private String handleDept;
    private String expectedFinishTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
