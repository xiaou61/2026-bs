package com.property.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitorRecord {
    private Long id;
    private String orderNo;
    private Long ownerId;
    private String visitorName;
    private String visitorPhone;
    private LocalDateTime visitTime;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
