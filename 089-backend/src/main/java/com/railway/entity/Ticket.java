package com.railway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("train_ticket")
public class Ticket {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private String ticketNo;
    private String passengerName;
    private String idCard;
    private String seatLabel;
    private String qrCode;
    private String status;
    private LocalDateTime verifyTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
