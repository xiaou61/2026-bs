package com.enterprise.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class RepairAppointmentVO {
    private Long id;
    private String orderNo;
    private Long userId;
    private Long serviceId;
    private Long sellerId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Integer status;
    private String remark;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String plateNo;
    private String vehicleModel;
    private String faultDesc;
    private LocalDateTime payTime;
    private LocalDateTime finishTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String buyerName;
    private String sellerName;
    private String serviceTitle;
}





