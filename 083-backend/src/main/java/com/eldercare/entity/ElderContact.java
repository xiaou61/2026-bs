package com.eldercare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("elder_contact")
public class ElderContact {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long elderId;
    private String contactName;
    private String relation;
    private String contactPhone;
    private String address;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
