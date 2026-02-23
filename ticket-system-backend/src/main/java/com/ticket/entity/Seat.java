package com.ticket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("seat")
public class Seat {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long showtimeId;
    private Integer rowNum;
    private Integer colNum;
    private String seatType;
    private BigDecimal price;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
