package com.railway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("train_schedule")
public class Schedule {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long trainId;
    private Long carriageId;
    private Long departureStationId;
    private Long arrivalStationId;
    private LocalDate travelDate;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal basePrice;
    private Integer totalSeats;
    private Integer availableSeats;
    private String saleStatus;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
