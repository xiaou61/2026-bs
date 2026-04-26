package com.hospital.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentOrder {
    private Long id;
    private String orderNo;
    private Long appointmentId;
    private Long userId;
    private BigDecimal amount;
    private Integer status;
    private String paymentMethod;
    private LocalDateTime payTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private String appointmentNo;
    private String patientName;
    private String doctorName;
    private String departmentName;
}
