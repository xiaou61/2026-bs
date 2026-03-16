package com.railway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("schedule_seat")
public class Seat {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long scheduleId;
    private Integer coachNo;
    private Integer rowNum;
    private Integer colNum;
    private String seatNo;
    private String seatType;
    private BigDecimal price;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
