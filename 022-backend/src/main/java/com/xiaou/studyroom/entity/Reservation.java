package com.xiaou.studyroom.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("reservation")
public class Reservation {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("seat_id")
    private Long seatId;

    @TableField("reservation_time")
    private LocalDateTime reservationTime;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    @TableField("check_in_time")
    private LocalDateTime checkInTime;

    @TableField("actual_end_time")
    private LocalDateTime actualEndTime;

    @TableField("status")
    private Integer status;

    @TableField("qrcode_content")
    private String qrcodeContent;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}