package com.cloudmonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("alert_notify")
public class AlertNotify {
    @TableId(type = IdType.AUTO)
private Long id;
private Long eventId;
private String channelName;
private String receiver;
private String sendStatus;
private String sendTime;
private String messageText;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
