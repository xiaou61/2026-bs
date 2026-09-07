package com.mfg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("alert_record")
public class AlertRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long equipmentId;
    private String alertType;
    private String alertLevel;
    private String content;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime handleTime;

    @TableField(exist = false)
    private String equipmentName;
}
