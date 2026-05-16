package com.vehicleclaim.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClaimApplication {
    private Long id;
    private String applicationNo;
    private String caseName;
    private String reportChannel;
    private String applicationTime;
    private String accidentLocation;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
