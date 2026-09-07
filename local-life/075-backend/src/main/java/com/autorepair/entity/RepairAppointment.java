package com.autorepair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@TableName("repair_appointment")
public class RepairAppointment {
    @TableId(type = IdType.AUTO)
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
}



