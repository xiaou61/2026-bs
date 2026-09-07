package com.classic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("service_order")
public class ServiceOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private Long planId;
    private String contactName;
    private String contactPhone;
    private LocalDate appointmentDate;
    private Integer status;
    private String remark;
    private String adminReply;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
