package com.xiaou.hair.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 预约实体类
 */
@Data
@TableName("appointment")
public class Appointment {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long storeId;
    
    private Long hairdresserId;
    
    private Long serviceId;
    
    private LocalDate appointmentDate;
    
    private LocalTime appointmentTime;
    
    private String status;
    
    private String cancelReason;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
