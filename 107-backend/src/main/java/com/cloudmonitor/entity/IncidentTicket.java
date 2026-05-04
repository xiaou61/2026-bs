package com.cloudmonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("incident_ticket")
public class IncidentTicket {
    @TableId(type = IdType.AUTO)
private Long id;
private String ticketNo;
private Long eventId;
private Long serverId;
private String title;
private String assignee;
private String priority;
private String status;
private String solution;
private String closedAt;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
