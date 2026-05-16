package com.psychologycare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("appointment_request")
public class AppointmentRequest {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String appointmentNo;
    private String caseTheme;
    private String applicantName;
    private String appointmentTime;
    private String appointmentStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}







