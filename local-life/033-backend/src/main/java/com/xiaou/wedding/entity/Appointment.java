package com.xiaou.wedding.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Appointment {
    private Long id;
    private Long customerId;
    private String appointmentType;
    private Long packageId;
    private Long photographerId;
    private Long studioId;
    private LocalDate appointmentDate;
    private String timeSlot;
    private String status;
    private String remark;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
