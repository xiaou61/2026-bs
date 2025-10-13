package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@TableName("time_slot")
public class TimeSlot {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String slotName;
    private String slotCode;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private LocalTime endTime;
    private Integer status;
    private Integer sortOrder;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}

