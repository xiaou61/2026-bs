package com.railway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("ticket_order")
public class TicketOrder {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private Long scheduleId;
    private String trainCode;
    private String departureStation;
    private String arrivalStation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String seatInfo;
    private String passengerNames;
    private String passengerInfo;
    private String contactPhone;
    private BigDecimal totalAmount;
    private BigDecimal payAmount;
    private String status;
    private LocalDateTime payTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
