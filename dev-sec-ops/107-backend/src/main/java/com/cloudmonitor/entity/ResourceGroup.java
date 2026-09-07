package com.cloudmonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("resource_group")
public class ResourceGroup {
    @TableId(type = IdType.AUTO)
private Long id;
private String groupName;
private String groupCode;
private String ownerName;
private String description;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
