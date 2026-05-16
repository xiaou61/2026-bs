package com.outpatientexam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamAppointment {
    private Long id;
    private String appointmentNo;
    private String itemName;
    private String appointmentDate;
    private String timeSlot;
    private String doctorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








