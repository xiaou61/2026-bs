package com.ticket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("cinema")
public class Cinema {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String businessHours;
    private String facilities;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
