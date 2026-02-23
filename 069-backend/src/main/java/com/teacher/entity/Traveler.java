package com.teacher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("traveler")
public class Traveler {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String name;
    private String certType;
    private String certNo;
    private String phone;
    private Integer isDefault;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}


