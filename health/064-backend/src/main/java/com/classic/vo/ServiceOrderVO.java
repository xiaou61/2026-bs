package com.classic.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ServiceOrderVO {
    private Long id;
    private String orderNo;
    private Long userId;
    private String userName;
    private Long planId;
    private String planName;
    private String contactName;
    private String contactPhone;
    private LocalDate appointmentDate;
    private Integer status;
    private String remark;
    private String adminReply;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
