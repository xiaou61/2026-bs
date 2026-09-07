package com.property.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingSlot {
    private Long id;
    private String slotNo;
    private String location;
    private Long ownerId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
