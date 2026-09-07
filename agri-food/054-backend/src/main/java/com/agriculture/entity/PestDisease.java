package com.agriculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("pest_disease")
public class PestDisease {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer type;
    private String cropType;
    private String symptom;
    private String harm;
    private String prevention;
    private String treatment;
    private String images;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
