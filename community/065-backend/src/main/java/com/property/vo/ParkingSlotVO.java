package com.property.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingSlotVO {
    private Long id;
    private String slotNo;
    private String location;
    private Long ownerId;
    private String ownerName;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
