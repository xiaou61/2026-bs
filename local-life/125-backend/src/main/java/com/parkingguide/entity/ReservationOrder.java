package com.parkingguide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("reservation_order")
public class ReservationOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String reservationNo;
    private String ownerName;
    private String lotName;
    private String spaceNo;
    private String reservationTime;
    private Integer durationMinute;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
