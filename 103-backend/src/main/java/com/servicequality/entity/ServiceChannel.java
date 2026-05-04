package com.servicequality.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("service_channel")
public class ServiceChannel {
    @TableId
    private Long id;
    private String channelName;
    private String channelType;
    private String ownerTeam;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
