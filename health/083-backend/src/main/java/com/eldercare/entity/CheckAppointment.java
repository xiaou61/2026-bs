package com.eldercare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("check_appointment")
public class CheckAppointment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String appointmentNo;
    private Long elderId;
    private Long packageId;
    private LocalDate appointmentDate;
    private String slotTime;
    private String source;
    private Integer status;
    private String remark;
    private Long createBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
