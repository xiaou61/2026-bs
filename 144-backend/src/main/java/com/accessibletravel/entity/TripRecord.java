package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TripRecord {
    private Long id;
    private String tripNo;
    private String travelerNo;
    private String travelDate;
    private String travelRoute;
    private String completionStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

