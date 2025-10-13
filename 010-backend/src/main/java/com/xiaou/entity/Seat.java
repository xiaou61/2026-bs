package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("seat")
public class Seat {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long areaId;
    private String seatNo;
    private String seatType;
    private Integer hasPower;
    private Integer hasLamp;
    private Integer positionX;
    private Integer positionY;
    private Integer hotScore;
    private Integer totalBookCount;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}

