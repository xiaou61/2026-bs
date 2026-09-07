package com.property.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RepairOrder {
    private Long id;
    private String orderNo;
    private Long houseId;
    private Long ownerId;
    private String title;
    private String content;
    private Integer status;
    private Long assigneeId;
    private String reply;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
