package com.legalcase.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentRecord {
    private Long id;
    private Long caseId;
    private Long clientId;
    private Long lawyerId;
    private LocalDateTime appointmentTime;
    private String appointmentType;
    private String location;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
