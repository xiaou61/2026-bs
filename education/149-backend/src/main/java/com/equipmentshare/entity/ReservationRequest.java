package com.equipmentshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("reservation_request")
public class ReservationRequest {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String reservationNo;
    private String equipmentName;
    private String applicantName;
    private String reservationTime;
    private String reservationStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








