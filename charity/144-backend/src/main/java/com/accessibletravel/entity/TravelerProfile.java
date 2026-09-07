package com.accessibletravel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TravelerProfile {
    private Long id;
    private String travelerNo;
    private String travelerName;
    private String phone;
    private String assistanceNeed;
    private String travelPreference;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
