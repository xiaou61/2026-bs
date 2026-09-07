package com.petcafe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("seat_info")
public class SeatInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long shopId;
    private String seatNo;
    private String zoneName;
    private Integer capacity;
    private java.math.BigDecimal minConsume;
    private String seatStatus;
    private String reservationStatus;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String shopName;
}
