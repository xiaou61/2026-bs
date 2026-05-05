package com.chargepile.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentOrder {
    private Long id;
    private String appointmentNo;
    private String ownerName;
    private String stationName;
    private String pileNo;
    private String appointmentTime;
    private Integer durationMinute;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
