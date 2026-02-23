package com.property.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RepairOrderVO {
    private Long id;
    private String orderNo;
    private Long houseId;
    private String houseName;
    private Long ownerId;
    private String ownerName;
    private String title;
    private String content;
    private Integer status;
    private Long assigneeId;
    private String assigneeName;
    private String reply;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
