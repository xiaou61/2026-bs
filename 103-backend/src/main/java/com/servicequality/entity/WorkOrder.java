package com.servicequality.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("work_order")
public class WorkOrder {
    @TableId
    private Long id;
    private String orderNo;
    private Long customerId;
    private Long agentId;
    private Long channelId;
    private String title;
    private String content;
    private String priority;
    private Integer status;
    private String solution;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
