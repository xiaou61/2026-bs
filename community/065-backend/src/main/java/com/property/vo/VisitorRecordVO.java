package com.property.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitorRecordVO {
    private Long id;
    private String orderNo;
    private Long ownerId;
    private String ownerName;
    private String visitorName;
    private String visitorPhone;
    private LocalDateTime visitTime;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
