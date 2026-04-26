package com.hospital.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AppointmentRecord {
    private Long id;
    private String appointmentNo;
    private Long userId;
    private Long cardId;
    private Long departmentId;
    private Long doctorId;
    private Long scheduleId;
    private Long orderId;
    private String patientName;
    private String contactPhone;
    private String remark;
    private Integer status;
    private LocalDate appointmentDate;
    private String timeSlot;
    private BigDecimal amount;
    private LocalDateTime payTime;
    private LocalDateTime finishTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private String doctorName;
    private String departmentName;
    private String cardNo;
    private String orderNo;
    private Integer orderStatus;
}
