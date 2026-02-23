package com.ticket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("showtime")
public class Showtime {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long movieId;
    private Long hallId;
    private Long cinemaId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal price;
    private String status;
    private Integer availableSeats;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
