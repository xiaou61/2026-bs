package com.railway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("train_info")
public class Train {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String trainCode;
    private String trainName;
    private String trainType;
    private Integer carriageCount;
    private Integer seatCount;
    private String coverUrl;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
