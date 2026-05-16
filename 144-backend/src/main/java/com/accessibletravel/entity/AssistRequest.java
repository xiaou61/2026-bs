package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AssistRequest {
    private Long id;
    private String requestNo;
    private String requestTitle;
    private String departurePoint;
    private String requestTime;
    private String destination;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
