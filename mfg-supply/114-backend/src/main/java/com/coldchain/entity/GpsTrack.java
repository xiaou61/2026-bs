package com.coldchain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GpsTrack {
    private Long id;
    private String trackNo;
    private String orderNo;
    private String vehicleNo;
    private String locationName;
    private String longitudeText;
    private String latitudeText;
    private Integer speedValue;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
