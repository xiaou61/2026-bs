package com.hospital.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DoctorReview {
    private Long id;
    private Long appointmentId;
    private Long doctorId;
    private Long userId;
    private Integer rating;
    private String content;
    private Integer status;
    private String patientName;
    private LocalDateTime createTime;

    private String doctorName;
}
