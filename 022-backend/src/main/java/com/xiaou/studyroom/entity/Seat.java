package com.xiaou.studyroom.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("seat")
public class Seat {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("room_id")
    private Long roomId;

    @TableField("seat_number")
    private String seatNumber;

    @TableField("seat_type")
    private Integer seatType;

    @TableField("seat_status")
    private Integer seatStatus;

    @TableField("x_coordinate")
    private BigDecimal xCoordinate;

    @TableField("y_coordinate")
    private BigDecimal yCoordinate;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}