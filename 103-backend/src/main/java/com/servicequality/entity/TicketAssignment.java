package com.servicequality.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("ticket_assignment")
public class TicketAssignment {
    @TableId
    private Long id;
    private Long orderId;
    private Long fromAgentId;
    private Long toAgentId;
    private String assignReason;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
