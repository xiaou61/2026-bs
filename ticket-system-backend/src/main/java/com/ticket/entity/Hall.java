package com.ticket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("hall")
public class Hall {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long cinemaId;
    private String name;
    private String type;
    private Integer totalSeats;
    private String seatLayout;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
